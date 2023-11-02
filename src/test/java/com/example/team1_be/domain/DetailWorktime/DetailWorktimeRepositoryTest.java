package com.example.team1_be.domain.DetailWorktime;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

class DetailWorktimeRepositoryTest extends BaseTest {

	public DetailWorktimeRepositoryTest(UserRepository userRepository,
		GroupRepository groupRepository,
		NotificationRepository notificationRepository,
		DetailWorktimeRepository dayRepository, ApplyRepository applyRepository,
		WeekRepository weekRepository,
		WorktimeRepository worktimeRepository,
		DetailWorktimeRepository detailWorktimeRepository,
		SubstituteRepository substituteRepository,
		EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, detailWorktimeRepository, substituteRepository, em);
	}

	@DisplayName("worktime과 date기반 조회")
	@Test
	void test() {
		LocalDate startWeekDate = LocalDate.parse("2023-10-09");
		Group group = groupRepository.findById(1L).orElse(null);
		List<Worktime> worktimes = worktimeRepository.findByDate(group.getId(), startWeekDate);
		assertThat(worktimes.size()).isNotEqualTo(0);

		List<DetailWorktime> weeklyDetailWorktimes = detailWorktimeRepository.findByStartDateAndWorktimes(startWeekDate,
			worktimes.stream().map(Worktime::getId).collect(
				Collectors.toList()));
		assertThat(weeklyDetailWorktimes.size()).isNotEqualTo(0);
		System.out.println(weeklyDetailWorktimes.size());
	}
}