package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendedWeeklyScheduleService {
	private final RecommendedWeeklyScheduleRepository recommendedWeeklyScheduleRepository;

	@Transactional
	public RecommendedWeeklySchedule creatRecommendedWeeklySchedule(
		RecommendedWeeklySchedule recommendedWeeklySchedule) {
		return recommendedWeeklyScheduleRepository.save(recommendedWeeklySchedule);
	}

	public List<RecommendedWeeklySchedule> findByWeek(Week week) {
		List<RecommendedWeeklySchedule> recommendedWeeklySchedules = recommendedWeeklyScheduleRepository.findByWeek(
			week.getId());
		if (recommendedWeeklySchedules.isEmpty()) {
			throw new NotFoundException("등록된 추천 스케줄이 존재하지 않습니다.");
		}
		return recommendedWeeklySchedules;
	}

	@Transactional
	public void deleteAll(List<RecommendedWeeklySchedule> recommendedWeeklySchedules) {
		recommendedWeeklyScheduleRepository.deleteAll(recommendedWeeklySchedules);
	}

	@Transactional
	public RecommendedWeeklySchedule creatRecommendedWeeklySchedule(Week week) {
		RecommendedWeeklySchedule weeklySchedule = RecommendedWeeklySchedule.builder()
			.week(week)
			.build();
		return creatRecommendedWeeklySchedule(weeklySchedule);
	}

	public Week getWeek(RecommendedWeeklySchedule recommendedWeeklySchedule) {
		return recommendedWeeklySchedule.getRecommendedWorktimeApplies()
			.get(0)
			.getApply()
			.getDetailWorktime()
			.getWorktime()
			.getWeek();
	}
}
