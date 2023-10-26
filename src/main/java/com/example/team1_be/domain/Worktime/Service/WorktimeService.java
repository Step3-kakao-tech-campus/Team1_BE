package com.example.team1_be.domain.Worktime.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorktimeService {
	private final WorktimeReadOnlyService worktimeReadOnlyService;
	private final WokrtimeWriteOnlyService wokrtimeWriteOnlyService;

	public List<Worktime> createWorktimes(List<Worktime> worktimes) {
		return wokrtimeWriteOnlyService.createWorktimes(worktimes);
	}

	public List<List<Worktime>> findWorktimesByDays(List<Day> days) {
		return worktimeReadOnlyService.findWorktimesByDays(days);
	}

	public List<Worktime> findByStartDateAndSchedule(LocalDate date, Schedule schedule) {
		return worktimeReadOnlyService.findByStartDateAndSchedule(date, schedule);
	}

	public List<Worktime> findBySpecificDateAndSchedule(LocalDate date, int dayOfWeek, Schedule schedule) {
		return worktimeReadOnlyService.findBySpecificDateAndSchedule(date, dayOfWeek, schedule);
	}
}
