package com.example.team1_be.domain.Week;

import static com.example.team1_be.domain.Week.WeekRecruitmentStatus.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.DetailWorktime.DetailWorktimeRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

class WeekRepositoryTest extends BaseTest {

	public WeekRepositoryTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DetailWorktimeRepository dayRepository,
		ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository,
		DetailWorktimeRepository detailWorktimeRepository, SubstituteRepository substituteRepository,
		EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, detailWorktimeRepository, substituteRepository, em);
	}

	@DisplayName("주간 일정 조회")
	@Test
	void shouldRetrieveWeeklySchedule() {
		assertThat(weekRepository.findById(1L).orElse(null))
			.isNotEqualTo(null);
		assertThat(weekRepository.findById(1L).orElse(null).getStatus())
			.isEqualTo(ENDED);
	}

	@DisplayName("시작일, 스케줄, 상태 기반 조회 성공")
	@Test
	void shouldRetrieveBasedOnStartDateScheduleAndStatus() {
		assertThat(weekRepository.findByScheduleIdStartDateAndStatus(1L, LocalDate.parse("2023-10-09"), ENDED)
			.orElse(null)).isNotEqualTo(null);
	}

	@DisplayName("시작일, 스케줄, 상태 기반 조회 실패")
	@Test
	void shouldFailToRetrieveBasedOnStartDateScheduleAndStatus() {
		assertThat(weekRepository.findByScheduleIdStartDateAndStatus(1L, LocalDate.parse("2023-10-09"), STARTED)
			.orElse(null)).isEqualTo(null);
	}
}