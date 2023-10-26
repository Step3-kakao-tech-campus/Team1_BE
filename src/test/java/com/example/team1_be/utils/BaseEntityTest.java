package com.example.team1_be.utils;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class BaseEntityTest extends BaseTest {

	public BaseEntityTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository,
		WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository,
		SubstituteRepository substituteRepository, EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, scheduleRepository, substituteRepository, em);
	}

	@DisplayName("미인증 상태로 repository 조회시 BaseEntity 정보를 확인할 수 있다.")
	@Test
	void test1() throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		System.out.println(om.writeValueAsString(userRepository.findAll()));
	}
}