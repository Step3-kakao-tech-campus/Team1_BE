package com.example.team1_be.domain.Apply.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyReadOnlyService {
	private final ApplyRepository repository;

	public List<Apply> findApplyByWorktimeIds(List<Long> worktimeIds) {
		log.info("{}개의 근무 시간 ID에 대한 신청 정보를 조회합니다.", worktimeIds.size());
		return repository.findAppliesByWorktimeIds(worktimeIds);
	}

	public List<Apply> findApplyByUserIdAndDateAndStatus(Long userId, LocalDate date, ApplyStatus status) {
		log.info("사용자 ID: {}, 날짜: {}, 상태: {}에 따른 신청 정보를 조회합니다.", userId, date, status);
		return repository.findByUserAndDateAndStatus(userId, date, status);
	}

	public List<User> findUsersByWorktimeIdAndApplyStatus(Long worktimeId, ApplyStatus status) {
		log.info("상세 근무 시간 ID: {}, 상태: {}에 따른 사용자를 조회합니다.", worktimeId, status);
		return repository.findUsersByWorktimeAndApplyStatus(worktimeId, status);
	}

	public Apply findApplyByUserIdAndWorktimeIdAndDay(Long userId, Long worktimeId, DayOfWeek day) {
		log.info("사용자 ID: {}, 근무 시간 ID: {}, 요일: {}에 따른 신청 정보를 조회합니다.", userId, worktimeId, day);
		return repository.findByUserAndWorktimeAndDay(userId, worktimeId, day).orElse(null);
	}

	public List<Apply> findApplyByUserIdAndDetailWorktimeIds(Long userId, List<Long> detailWorktimeIds) {
		log.info("사용자 ID: {}, 상세 근무 시간 ID: {}에 따른 신청 정보를 조회합니다.", userId, detailWorktimeIds);
		return repository.findByUserAndDetailWorktimeIds(userId, detailWorktimeIds);
	}
}
