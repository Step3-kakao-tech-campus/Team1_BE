package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeekService {
	private final WeekReadOnlyService weekReadOnlyService;
	private final WeekWriteService weekWriteService;

	public Week createWeek(Schedule schedule, LocalDate weekStartDate) {
		return weekWriteService.createWeek(schedule, weekStartDate);
	}

	public void updateWeekStatus(Week week, WeekRecruitmentStatus weekRecruitmentStatus) {
		weekWriteService.updateWeekStatus(week, weekRecruitmentStatus);
	}

	public Week findByScheduleAndStartDate(Schedule schedule, LocalDate startDate) {
		return weekReadOnlyService.findByScheduleAndStartDate(schedule, startDate);
	}

	public List<Week> findLatestByScheduleAndStatus(Schedule schedule, WeekRecruitmentStatus status) {
		return weekReadOnlyService.findLatestByScheduleAndStatus(schedule, status);
	}

	public Week findByScheduleIdStartDateAndStatus(Schedule schedule, LocalDate startDate,
		WeekRecruitmentStatus status) {
		return weekReadOnlyService.findByScheduleIdStartDateAndStatus(schedule, startDate, status);
	}

	public List<Week> findByScheduleAndYearMonthAndStatus(LocalDate date, LocalDate toDate, Schedule schedule,
		WeekRecruitmentStatus weekRecruitmentStatus) {
		return weekReadOnlyService.findByScheduleAndYearMonthAndStatus(date, toDate, schedule, weekRecruitmentStatus);
	}
}
