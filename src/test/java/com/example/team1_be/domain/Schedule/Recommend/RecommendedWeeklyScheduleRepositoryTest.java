package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecommendedWeeklyScheduleRepositoryTest extends BaseTest {

    public RecommendedWeeklyScheduleRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, MemberRepository memberRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, memberRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @Autowired
    private RecommendedWeeklyScheduleRepository recommendedWeeklyScheduleRepository;
    @Autowired
    private RecommendedWorktimeApplyRepository recommendedWorktimeApplyRepository;
    @Autowired
    private EntityManager em;

    @DisplayName("추천 주간 스케줄 조회")
    @Test
    void test1() {
        LocalDate selectedDate = LocalDate.parse("2023-10-16");
        User user = userRepository.findById(1L).orElse(null);
        assertThat(user).isNotEqualTo(null);

        Group group = groupRepository.findByUser(1L).orElse(null);
        assertThat(group).isNotEqualTo(null);

        Schedule schedule = scheduleRepository.findByGroup(group).orElse(null);
        assertThat(schedule).isNotEqualTo(null);

        List<Worktime> weeklyWorktimes = worktimeRepository.findByStartDateAndScheduleId(selectedDate, schedule.getId());

        List<Long> worktimeIds = weeklyWorktimes.stream()
                .map(Worktime::getId)
                .collect(Collectors.toList());

        List<Apply> applyList = applyRepository.findAppliesByWorktimeIds(worktimeIds);

        Map<Long,Integer> requestMap = weeklyWorktimes.stream()
                .collect(Collectors.toMap(Worktime::getId, Worktime::getAmount));

        SchduleGenerator generator = new SchduleGenerator(applyList, requestMap);
        List<List<Apply>> generatedSchedules = generator.generateSchedule();

        for (List<Apply> generatedSchedule:generatedSchedules) {
            RecommendedWeeklySchedule weeklySchedule = RecommendedWeeklySchedule.builder()
                    .user(user)
                    .build();
            recommendedWeeklyScheduleRepository.save(weeklySchedule);

            List<RecommendedWorktimeApply> recommendedWorktimeApplies = new ArrayList<>();
            for (Worktime worktime : weeklyWorktimes) {
                List<Apply> applies = generatedSchedule.stream()
                        .filter(x -> x.getWorktime().getId().equals(worktime.getId()))
                        .collect(Collectors.toList());

                for(Apply apply: applies) {
                    recommendedWorktimeApplies.add(RecommendedWorktimeApply.builder()
                                    .recommendedWeeklySchedule(weeklySchedule)
                                    .apply(apply)
                            .build());
                }
            }

            recommendedWorktimeApplyRepository.saveAll(recommendedWorktimeApplies);
        }
        em.flush();
        em.clear();

    }
}