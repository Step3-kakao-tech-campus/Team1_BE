package com.example.team1_be.domain.Substitute;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyType;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Day.Weekday;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubstitueRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private WorktimeRepository worktimeRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private SubstituteRepository substituteRepository;

    @DisplayName("대타 신청서를 생성할 수 있다.")
    @Test
    void test1() {
        Substitute.builder()
                .content("이런 사유로 대타 신청합니다.")
                .adminApprove(false)
                .build();
    }

    @DisplayName("대타 신청서를 저장할 수 있다.")
    @Test
    void test2() {
        Group group = Group.builder()
                .address("부산광역시")
                .name("맘스터치")
                .phoneNumber("010-2222-2222")
                .build();
        groupRepository.save(group);

        Schedule schedule = Schedule.builder()
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        Week week = Week.builder()
                .schedule(schedule)
                .startTime(LocalDateTime.now())
                .build();
        weekRepository.save(week);

        Day day = Day.builder()
                .week(week)
                .weekday(Weekday.Monday)
                .build();
        dayRepository.save(day);

        Worktime worktime = Worktime.builder()
                .day(day)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();
        worktimeRepository.save(worktime);

        User user = User.builder()
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();
        userRepository.save(user);

        Member member = Member.builder()
                .user(user)
                .isAdmin(false)
                .group(group)
                .build();
        memberRepository.save(member);

        Apply apply = Apply.builder()
                .state(ApplyType.REMAIN)
                .worktime(worktime)
                .member(member)
                .build();
        applyRepository.save(apply);

        Substitute substitute = Substitute.builder()
                .content("이런 사유로 대타 신청합니다.")
                .adminApprove(false)
                .applicant(apply)
                .build();
        substitueRepository.save(substitute);
    }
}