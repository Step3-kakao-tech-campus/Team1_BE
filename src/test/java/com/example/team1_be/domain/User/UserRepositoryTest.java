package com.example.team1_be.domain.User;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends BaseTest {

    public UserRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @DisplayName("사용자 조회")
    @Test
    void test1() {
        User user = User.builder()
                .kakaoId(7L)
                .name("dlwogns")
                .phoneNumber("010-1111-1111")
                .isAdmin(true)
                .build();

        userRepository.save(user);

        assertThat(userRepository.findById(1L).orElse(null))
                .isNotEqualTo(null);
    }

    @DisplayName("사용자 전체 조회")
    @Test
    void test2() {
        List<User> users = userRepository.findAll();

        users.stream().forEach(user -> System.out.println(user.getId()));
    }
}