package com.example.team1_be.domain.DetailWorktime.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.DetailWorktime.DetailWorktimeRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailWorktimeReadOnlyService {
	private final DetailWorktimeRepository repository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public List<DetailWorktime> findByGroupAndDateAndStatus(Group group, LocalDate date, WeekRecruitmentStatus status) {
		List<DetailWorktime> detailWorktimes = repository.findByGroupAndDatesAndStatus(group.getId(), date, status);
		logger.info("그룹 ID: {}, 날짜: {}, 상태: {}에 따른 상세 근무 시간 정보를 조회하였습니다.", group.getId(), date, status);
		return detailWorktimes;
	}

	public List<Apply> findAppliesByWorktimeAndStatus(Worktime worktime, ApplyStatus status, DayOfWeek day) {
		DetailWorktime detailWorktime = repository.findByWorktimeAndStatus(worktime.getId(), day)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 세부 근무 일정입니다."));
		logger.info("근무 시간 ID: {}, 요일: {}에 따른 신청 정보를 조회하였습니다.", worktime.getId(), day);
		return detailWorktime.getApplies();
	}

	public List<DetailWorktime> findByStartDateAndGroup(LocalDate date, Group group) {
		List<DetailWorktime> detailWorktimes = repository.findByStartDateAndGroup(date, group.getId());
		if (detailWorktimes.isEmpty()) {
			logger.warn("시작 날짜: {}, 그룹 ID: {}에 따른 상세 근무 시간 정보를 찾지 못하였습니다.", date, group.getId());
			throw new CustomException("등록된 근무일정이 없습니다.", HttpStatus.NOT_FOUND);
		}
		logger.info("시작 날짜: {}, 그룹 ID: {}에 따른 상세 근무 시간 정보를 조회하였습니다.", date, group.getId());
		return detailWorktimes;
	}

	public List<DetailWorktime> findByGroupAndDate(Group group, LocalDate selectedDate) {
		List<DetailWorktime> detailWorktimes = repository.findByGroupAndDate(group.getId(), selectedDate);
		if (detailWorktimes.isEmpty()) {
			logger.warn("그룹 ID: {}, 선택된 날짜: {}에 따른 상세 근무 시간 정보를 찾지 못하였습니다.", group.getId(), selectedDate);
			throw new CustomException("등록된 근무일정이 없습니다.", HttpStatus.NOT_FOUND);
		}
		logger.info("그룹 ID: {}, 선택된 날짜: {}에 따른 상세 근무 시간 정보를 조회하였습니다.", group.getId(), selectedDate);
		return detailWorktimes;
	}

	public List<DetailWorktime> findByStartDateAndWorktimes(LocalDate date, List<Long> ids) {
		List<DetailWorktime> detailWorktimes = repository.findByStartDateAndWorktimes(date, ids);
		logger.info("시작 날짜: {}, 근무 시간 ID 목록: {}에 따른 상세 근무 시간 정보를 조회하였습니다.", date, ids);
		return detailWorktimes;
	}

	public List<DetailWorktime> findByDayAndWorktimeIds(DayOfWeek day, List<Long> worktimeIds) {
		List<DetailWorktime> detailWorktimes = repository.findDayAndWorktimeIds(day, worktimeIds);
		if (detailWorktimes.isEmpty()) {
			logger.warn("요일: {}, 근무 시간 ID 목록: {}에 따른 상세 근무 시간 정보를 찾지 못하였습니다.", day, worktimeIds);
			throw new NotFoundException("Worktime은 생성되었지만 DetailWorktime이 생성되지 않은 논리적 오류입니다.");
		}
		logger.info("요일: {}, 근무 시간 ID 목록: {}에 따른 상세 근무 시간 정보를 조회하였습니다.", day, worktimeIds);
		return detailWorktimes;
	}
}
