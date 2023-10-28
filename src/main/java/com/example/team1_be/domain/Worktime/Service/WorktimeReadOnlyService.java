package com.example.team1_be.domain.Worktime.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorktimeReadOnlyService {
	private final WorktimeRepository repository;

	public List<Worktime> findByGroupAndDate(Group group, LocalDate date) {
		List<Worktime> worktimes = repository.findByDate(group.getId(), date);
		if (worktimes.isEmpty()) {
			throw new NotFoundException("해당 날짜에 등록된 스케줄이 없습니다.");
		}
		return worktimes;
	}

	public List<Worktime> findByWeek(Week week) {
		List<Worktime> worktimes = repository.findByWeek(week);
		if (worktimes.isEmpty()) {
			throw new NotFoundException("해당 날짜에 등록된 스케줄이 없습니다.");
		}
		return worktimes;
	}
}