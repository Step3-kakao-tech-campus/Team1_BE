package com.example.team1_be.domain.User;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.DetailWorktime.DetailWorktimeRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

class UserRepositoryTest extends BaseTest {

	public UserRepositoryTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DetailWorktimeRepository dayRepository,
		ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository,
		DetailWorktimeRepository detailWorktimeRepository, SubstituteRepository substituteRepository,
		EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, detailWorktimeRepository, substituteRepository, em);
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