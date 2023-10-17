package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Schedule.DTO.WeeklyScheduleCheck;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import com.example.team1_be.utils.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;
    private final WeekRepository weekRepository;
    private final DayRepository dayRepository;
    private final WorktimeRepository worktimeRepository;
    private final ApplyRepository applyRepository;

    @Transactional
    public void recruitSchedule(User user, RecruitSchedule.Request request) {
        if (request.getWeeklyAmount().size() != 7){
            throw new CustomException("모든 요일에 대한 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        // member 찾기
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("등록되지 않은 멤버입니다.", HttpStatus.NOT_FOUND));

        // group 찾기
        Group group = member.getGroup();

        // 스케줄 생성
        Schedule schedule = Schedule.builder()
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        // week 생성
        Week week = Week.builder()
                .schedule(schedule)
                .status(WeekRecruitmentStatus.STARTED)
                .startDate(request.getWeekStartDate())
                .build();
        weekRepository.save(week);

        // 각 요일 정보(Day) 기입
        List<RecruitSchedule.Request.DailySchedule> weeklyAmount = request.getWeeklyAmount();
        List<Day> days = IntStream.range(1, weeklyAmount.size() + 1)
                .mapToObj(dayOfWeek -> Day.builder()
                        .week(week)
                        .dayOfWeek(dayOfWeek)
                        .build()
                ).collect(Collectors.toList());
        dayRepository.saveAll(days);

        List<Worktime> worktimeList = new ArrayList<>();
        IntStream.range(0, days.size())
                .forEach(dayIdx -> weeklyAmount.get(dayIdx)
                        .getDailySchedules()
                        .forEach(worktime-> worktimeList.add(Worktime.builder()
                                        .title(worktime.getTitle())
                                        .startTime(worktime.getStartTime())
                                        .endTime(worktime.getEndTime())
                                        .amount(worktime.getAmount())
                                        .day(days.get(dayIdx))
                                .build())));
        worktimeRepository.saveAll(worktimeList);
    }

    public WeeklyScheduleCheck.Response weeklyScheduleCheck(User user, LocalDate request) {
        Group group = groupRepository.findByUser(user.getId())
                .orElseThrow(() -> new CustomException("그룹에 가입되어있지 않습니다.", HttpStatus.FORBIDDEN));

        Schedule schedule = scheduleRepository.findByGroup(group)
                .orElseThrow(() -> new CustomException("등록된 스케줄이 없습니다.", HttpStatus.FORBIDDEN));

        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST));

        Week week = null;
        if (member.getIsAdmin()) {
            week = weekRepository.findByScheduleIdStartDateAndAndStatus(schedule.getId(), request, WeekRecruitmentStatus.STARTED)
                    .orElseThrow(() -> new CustomException("모집 중인 스케줄을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        } else {
            week = weekRepository.findByScheduleIdStartDateAndAndStatus(schedule.getId(), request, WeekRecruitmentStatus.ENDED)
                    .orElseThrow(() -> new CustomException("모집 완료된 스케줄이 없습니다.", HttpStatus.NOT_FOUND));
        }

        List<Day> days = dayRepository.findByWeekId(week.getId());
        if (days.size() == 0) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<List<Worktime>> weeklyWorktime = days.stream().map(day -> worktimeRepository.findByDayId(day.getId())).collect(Collectors.toList());
        List<List<List<Apply>>> applyList = weeklyWorktime.stream()
                .map(worktimes -> worktimes.stream()
                        .map(worktime -> applyRepository.findappliesByWorktimeId(worktime.getId()))
                        .collect(Collectors.toList())).collect(Collectors.toList());

        return new WeeklyScheduleCheck.Response(weeklyWorktime, applyList);
    }
}
