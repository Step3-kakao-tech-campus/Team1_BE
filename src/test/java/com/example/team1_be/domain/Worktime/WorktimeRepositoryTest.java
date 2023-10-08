package com.example.team1_be.domain.Worktime;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorktimeRepositoryTest extends BaseTest {

    public WorktimeRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, MemberRepository memberRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, memberRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @DisplayName("일별 근무 시간 조회")
    @Test
    void test1() {
        Week week = weekRepository.findById(1L).orElse(null);
        Day firstOfDay = dayRepository.findByWeekId(week.getId()).get(0);
        List<Worktime> monday = worktimeRepository.findByDayId(firstOfDay.getId());
        assertThat(monday.size()).isEqualTo(3);
    }
}