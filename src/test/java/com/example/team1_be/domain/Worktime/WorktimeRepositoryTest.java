package com.example.team1_be.domain.Worktime;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRepository;

class WorktimeRepositoryTest extends BaseTest {

	public WorktimeRepositoryTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository,
		WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository,
		SubstituteRepository substituteRepository, EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, scheduleRepository, substituteRepository, em);
	}

	@DisplayName("일별 근무 시간 조회")
	@Test
	void test1() {
		Week week = weekRepository.findById(1L).orElse(null);
		Day firstOfDay = dayRepository.findByWeekId(week.getId()).get(0);
		List<Worktime> monday = worktimeRepository.findByDayId(firstOfDay.getId());
		assertThat(monday.size()).isEqualTo(3);
	}

	@DisplayName("양방향 매핑 조회 worktime 측면")
	@Test
	void bidirectionalSelect1() {
		Worktime worktime = worktimeRepository.findById(1L)
			.orElse(null);
		assertThat(worktime).isNotEqualTo(null);
		List<Apply> applyList = worktime.getApplyList();
		assertThat(applyList.size()).isNotEqualTo(0);
	}

	@DisplayName("양방향 매핑 조회 apply 측면")
	@Test
	void bidirectionalSelect2() {
		Apply apply = applyRepository.findById(1L)
			.orElse(null);
		assertThat(apply).isNotEqualTo(null);
		Worktime worktime = apply.getWorktime();
		System.out.println(worktime.getId());
	}

	@DisplayName("일간 확정 근무 명단 조회")
	@Test
	void getDailyFixedApplies() {
		// given
		LocalDate selectedDate = LocalDate.parse("2023-10-09");

		// when
		User user = userRepository.findById(1L).orElse(null);
		assertThat(user).isNotEqualTo(null);

		Group group = userRepository.findGroupByUser(user.getId()).orElse(null);
		assertThat(group).isNotEqualTo(null);

		Schedule schedule = scheduleRepository.findByGroup(group).orElse(null);
		assertThat(schedule).isNotEqualTo(null);

		LocalDate date = selectedDate.minusDays(selectedDate.getDayOfWeek().getValue() - 1);
		int dayOfWeek = selectedDate.getDayOfWeek().getValue();
		List<Worktime> worktimes = worktimeRepository.findBySpecificDateAndScheduleId(date, dayOfWeek,
			schedule.getId());
		assertThat(worktimes.size()).isNotEqualTo(0);
		System.out.println("worktime : " + worktimes.size());
	}
}