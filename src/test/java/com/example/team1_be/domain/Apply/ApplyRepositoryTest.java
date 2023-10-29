package com.example.team1_be.domain.Apply;

import javax.persistence.EntityManager;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.DetailWorktime.DetailWorktimeRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

class ApplyRepositoryTest extends BaseTest {
	public ApplyRepositoryTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DetailWorktimeRepository dayRepository,
		ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository,
		DetailWorktimeRepository detailWorktimeRepository, SubstituteRepository substituteRepository,
		EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, detailWorktimeRepository, substituteRepository, em);
	}
}