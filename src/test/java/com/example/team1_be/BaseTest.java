package com.example.team1_be;

import javax.persistence.EntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql("/data.sql")
public class BaseTest {
	protected final UserRepository userRepository;
	protected final GroupRepository groupRepository;
	protected final NotificationRepository notificationRepository;
	protected final DayRepository dayRepository;
	protected final ApplyRepository applyRepository;
	protected final WeekRepository weekRepository;
	protected final WorktimeRepository worktimeRepository;
	protected final ScheduleRepository scheduleRepository;
	protected final SubstituteRepository substituteRepository;
	protected final EntityManager em;

	public BaseTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository,
		WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository,
		SubstituteRepository substituteRepository, EntityManager em) {
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
		this.notificationRepository = notificationRepository;
		this.dayRepository = dayRepository;
		this.applyRepository = applyRepository;
		this.weekRepository = weekRepository;
		this.worktimeRepository = worktimeRepository;
		this.scheduleRepository = scheduleRepository;
		this.substituteRepository = substituteRepository;
		this.em = em;
	}
}
