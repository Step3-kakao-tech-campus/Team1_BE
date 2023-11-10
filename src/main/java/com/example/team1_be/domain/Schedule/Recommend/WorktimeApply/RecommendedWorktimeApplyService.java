package com.example.team1_be.domain.Schedule.Recommend.WorktimeApply;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendedWorktimeApplyService {
	private final RecommendedWorktimeApplyRepository repository;

	@Transactional
	public void createRecommendedWorktimeApplies(List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
		log.info("추천 작업 시간 적용을 생성합니다.");
		repository.saveAll(recommendedWorktimeApplies);
		log.info("추천 작업 시간 적용 생성이 완료되었습니다.");
	}

	@Transactional
	public void deleteAll(List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
		log.info("모든 추천 작업 시간 적용을 삭제합니다.");
		repository.deleteAll(recommendedWorktimeApplies);
		log.info("모든 추천 작업 시간 적용 삭제가 완료되었습니다.");
	}
}
