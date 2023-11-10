package com.example.team1_be.domain.Apply.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyReadOnlyService {
	private final ApplyRepository repository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public List<Apply> findAppliesByWorktimes(List<Long> worktimeIds) {
		logger.info("{}개의 근무 시간 ID에 대한 신청 정보를 조회합니다.", worktimeIds.size());
		return repository.findAppliesByWorktimeIds(worktimeIds);
	}

	public List<Apply> findByUserAndDateAndStatus(User user, LocalDate date, ApplyStatus status) {
		logger.info("사용자 ID: {}, 날짜: {}, 상태: {}에 따른 신청 정보를 조회합니다.", user.getId(), date, status);
		return repository.findByUserAndDateAndStatus(user.getId(), date, status);
	}

	public List<User> findUsersByWorktimeAndApplyStatus(DetailWorktime worktime, ApplyStatus status) {
		logger.info("상세 근무 시간 ID: {}, 상태: {}에 따른 사용자를 조회합니다.", worktime.getId(), status);
		return repository.findUsersByWorktimeAndApplyStatus(worktime.getId(), status);
	}

	public Apply findByUserAndWorktimeAndDay(User user, Worktime worktime, DayOfWeek day) {
		logger.info("사용자 ID: {}, 근무 시간 ID: {}, 요일: {}에 따른 신청 정보를 조회합니다.", user.getId(), worktime.getId(), day);
		return repository.findByUserAndWorktimeAndDay(user.getId(), worktime.getId(), day).orElse(null);
	}

	public List<Apply> findByUserAndDetailWorktimeIds(User user, List<Long> detailWorktimeIds) {
		logger.info("사용자 ID: {}, 상세 근무 시간 ID: {}에 따른 신청 정보를 조회합니다.", user.getId(), detailWorktimeIds);
		return repository.findByUserAndDetailWorktimeIds(user.getId(), detailWorktimeIds);
	}
}
