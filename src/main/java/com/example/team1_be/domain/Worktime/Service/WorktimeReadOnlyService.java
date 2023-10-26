package com.example.team1_be.domain.Worktime.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import com.example.team1_be.utils.errors.exception.BadRequestException;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorktimeReadOnlyService {
	private final WorktimeRepository worktimeRepository;

	public List<List<Worktime>> findWorktimesByDays(List<Day> days) {
		return days.stream().map(day -> worktimeRepository.findByDayId(day.getId())).collect(Collectors.toList());
	}

	public List<Worktime> findByStartDateAndSchedule(LocalDate date, Schedule schedule) {
		List<Worktime> worktimes = worktimeRepository.findByStartDateAndScheduleId(date, schedule.getId());
		if (worktimes.isEmpty()) {
			throw new CustomException("등록된 근무일정이 없습니다.", HttpStatus.NOT_FOUND);
		}
		return worktimes;
	}

	public List<Worktime> findBySpecificDateAndSchedule(LocalDate date, int dayOfWeek, Schedule schedule) {
		List<Worktime> worktimes = worktimeRepository.findBySpecificDateAndScheduleId(date, dayOfWeek,
			schedule.getId());
		if (worktimes.isEmpty()) {
			throw new BadRequestException("확정된 스케줄이 아닙니다.");
		}
		return worktimes;
	}
}
