package com.example.team1_be.domain.Week;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static com.example.team1_be.domain.Week.WeekRecruitmentStatus.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WeekRepositoryTest extends BaseTest {

    public WeekRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, MemberRepository memberRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, memberRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @DisplayName("주간 일정 조회")
    @Test
    void test1() {
        assertThat(weekRepository.findById(1L).orElse(null))
                .isNotEqualTo(null);
        assertThat(weekRepository.findById(1L).orElse(null).getStatus())
                .isEqualTo(STARTED);
    }
}