package com.example.team1_be.domain.Worktime.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorktimeService {
	private final WorktimeReadOnlyService readOnlyService;
	private final WokrtimeWriteOnlyService writeOnlyService;

	public List<Worktime> createWorktimes(List<Worktime> worktimes) {
		return writeOnlyService.createWorktimes(worktimes);
	}

	public List<Worktime> createWorktimes(Week week, List<Worktime> weeklyWorktimes) {
		weeklyWorktimes.forEach(worktime -> worktime.updateWeek(week));
		return createWorktimes(weeklyWorktimes);
	}

	public List<Worktime> findByGroupAndDate(Group group, LocalDate date) {
		return readOnlyService.findByGroupAndDate(group, date);
	}

	public List<Worktime> findByWeekAndDate(Week week) {
		return readOnlyService.findByWeek(week);
	}
}
