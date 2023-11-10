package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendedWeeklyScheduleService {
	private final RecommendedWeeklyScheduleRepository recommendedWeeklyScheduleRepository;

	@Transactional
	public RecommendedWeeklySchedule creatRecommendedWeeklySchedule(
		RecommendedWeeklySchedule recommendedWeeklySchedule) {
		log.info("추천 주간 스케줄을 생성합니다.");
		RecommendedWeeklySchedule result = recommendedWeeklyScheduleRepository.save(recommendedWeeklySchedule);
		log.info("추천 주간 스케줄 생성이 완료되었습니다.");
		return result;
	}

	public List<RecommendedWeeklySchedule> findByWeek(Week week) {
		log.info("주별로 추천 스케줄을 찾습니다.");
		List<RecommendedWeeklySchedule> recommendedWeeklySchedules = recommendedWeeklyScheduleRepository.findByWeek(
			week.getId());
		if (recommendedWeeklySchedules.isEmpty()) {
			log.error("등록된 추천 스케줄이 존재하지 않습니다.");
			throw new NotFoundException("등록된 추천 스케줄이 존재하지 않습니다.");
		}
		log.info("주별로 추천 스케줄 찾기가 완료되었습니다.");
		return recommendedWeeklySchedules;
	}

	@Transactional
	public void deleteAll(List<RecommendedWeeklySchedule> recommendedWeeklySchedules) {
		log.info("모든 추천 주간 스케줄을 삭제합니다.");
		recommendedWeeklyScheduleRepository.deleteAll(recommendedWeeklySchedules);
		log.info("모든 추천 주간 스케줄 삭제가 완료되었습니다.");
	}

	@Transactional
	public RecommendedWeeklySchedule creatRecommendedWeeklySchedule(Week week) {
		log.info("주에 따른 추천 주간 스케줄을 생성합니다.");
		RecommendedWeeklySchedule weeklySchedule = RecommendedWeeklySchedule.builder()
			.week(week)
			.build();
		RecommendedWeeklySchedule result = creatRecommendedWeeklySchedule(weeklySchedule);
		log.info("주에 따른 추천 주간 스케줄 생성이 완료되었습니다.");
		return result;
	}

	public Week getWeek(RecommendedWeeklySchedule recommendedWeeklySchedule) {
		log.info("추천 주간 스케줄에서 주를 가져옵니다.");
		Week week = recommendedWeeklySchedule.getRecommendedWorktimeApplies()
			.get(0)
			.getApply()
			.getDetailWorktime()
			.getWorktime()
			.getWeek();
		log.info("추천 주간 스케줄에서 주 가져오기가 완료되었습니다.");
		return week;
	}
}
