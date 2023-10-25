package com.example.team1_be.domain.Day;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DayRepositoryTest extends BaseTest {

    public DayRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @DisplayName("일별 일정 조회")
    @Test
    void test1() {
        assertThat(dayRepository.findById(1L)
                .orElse(null)).isNotEqualTo(null);
    }

    @DisplayName("주간 ID로 days 조회")
    @Test
    void test2() {
        List<Day> days = dayRepository.findByWeekId(1L);
        assertThat(days.size()).isEqualTo(7);
    }
}