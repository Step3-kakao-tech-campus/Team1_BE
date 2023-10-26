package com.example.team1_be.domain.Group;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

class GroupRepositoryTest extends BaseTest {

	public GroupRepositoryTest(UserRepository userRepository, GroupRepository groupRepository,
		NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository,
		WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository,
		SubstituteRepository substituteRepository, EntityManager em) {
		super(userRepository, groupRepository, notificationRepository, dayRepository, applyRepository, weekRepository,
			worktimeRepository, scheduleRepository, substituteRepository, em);
	}

	@DisplayName("그룹 조회")
	@Test
	void test1() {
		Group group = Group.builder()
			.id(1L)
			.name("이재훈")
			.phoneNumber("010-5538-6818")
			.address("부산광역시")
			.name("맘스터치")
			.phoneNumber("010-7777-7777")
			.build();
		groupRepository.save(group);

		assertThat(groupRepository.findById(1L).orElse(null))
			.isNotEqualTo(null);
	}

	@DisplayName("그룹 전체 조회")
	@Test
	void test2() {
		Group group = Group.builder()
			.id(1L)
			.name("이재훈")
			.phoneNumber("010-5538-6818")
			.address("부산광역시")
			.build();
		groupRepository.save(group);
	}

	@DisplayName("그룹을 조회할 수 있다.")
	@Test
	void test3() {
		Group group = Group.builder()
			.id(1L)
			.name("이재훈")
			.phoneNumber("010-5538-6818")
			.address("부산광역시")
			.build();
		groupRepository.save(group);
		Group group1 = groupRepository.findById(1L).orElse(null);
		assertThat(group1).isNotEqualTo(null);
	}

	//    @DisplayName("초대 링크 보내기")
	//    @Test
	//    String test4(){
	//        Group group = Group.builder()
	//                .id(1L)
	//                .name("이재훈")
	//                .phoneNumber("010-5538-6818")
	//                .address("부산광역시")
	//                .build();
	//        groupRepository.save(group);
	//
	//        String inviteCode = "1234";
	//
	//
	//
	//    }
}