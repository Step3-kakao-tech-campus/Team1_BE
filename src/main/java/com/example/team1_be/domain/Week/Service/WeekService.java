package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.team1_be.utils.errors.ClientErrorCode;
import com.example.team1_be.utils.errors.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WeekService {
	private final WeekReadOnlyService readOnlyService;
	private final WeekWriteService writeService;

	public Week createWeek(Group group, LocalDate weekStartDate) {
		log.info("주차를 생성합니다.");
		Week week = writeService.createWeek(group, weekStartDate);
		log.info("주차 생성이 완료되었습니다.");
		return week;
	}

	public void updateWeekStatus(Week week, WeekRecruitmentStatus weekRecruitmentStatus) {
		log.info("주차 상태를 업데이트합니다.");
		writeService.updateWeekStatus(week, weekRecruitmentStatus);
		log.info("주차 상태 업데이트가 완료되었습니다.");
	}

	public Week findByGroupAndStartDate(Group group, LocalDate startDate) {
		log.info("그룹과 시작 날짜에 따른 주차를 찾습니다.");
		Week week = readOnlyService.findByGroupAndStartDate(group, startDate);
		if (null == week) {
			log.info("해당 주차를 찾을 수 없습니다.");
			throw new BadRequestException("해당 주차를 찾을 수 없습니다.", ClientErrorCode.RECRUITMENT_NOT_STARTED);
		}
		log.info("그룹과 시작 날짜에 따른 주차를 찾았습니다.");
		return week;
	}

	public List<Worktime> findWorktimes(Week week) {
		log.info("주차의 근무 시간을 찾습니다.");
		List<Worktime> worktimes = week.getWorktimes();
		if (null == worktimes) {
			log.info("근무일정이 존재하지 않습니다.");
			throw new NotFoundException("근무일정이 존재하지 않습니다.");
		}
		log.info("주차의 근무 시간을 찾았습니다.");
		return worktimes;
	}

	public Week findLatestByGroup(Group group) {
		log.info("그룹의 최신 주차를 찾습니다.");
		Week week = readOnlyService.findByGroupAndStatus(group, WeekRecruitmentStatus.ENDED);
		log.info("그룹의 최신 주차를 찾았습니다.");
		return week;
	}

	public WeekRecruitmentStatus getWeekStatus(Group group, LocalDate startDate) {
		log.info("그룹과 시작 날짜에 따른 주차 상태를 얻습니다.");
		Week week = readOnlyService.findByGroupAndStartDate(group, startDate);
		if (null == week) {
			log.info("해당 주차 상태를 찾을 수 없습니다.");
			return null;
		} else {
			log.info("해당 주차 상태를 찾았습니다.");
			return week.getStatus();
		}
	}

	public void checkAppliable(User user, Week week) {
		log.info("주차가 적용 가능한지 확인합니다.");
		if (user.getIsAdmin() && week.getStatus().equals(WeekRecruitmentStatus.ENDED)) {
			log.info("이미 마감된 스케줄입니다.");
			throw new BadRequestException("이미 마감된 스케줄입니다.", ClientErrorCode.RECRUITMENT_CLOSED);
		}
		if (!user.getIsAdmin() && week.getStatus().equals(WeekRecruitmentStatus.STARTED)) {
			log.info("확정된 스케줄이 아닙니다.");
			throw new NotFoundException("확정된 스케줄이 아닙니다.");
		}
		log.info("주차가 적용 가능합니다.");
	}
}
