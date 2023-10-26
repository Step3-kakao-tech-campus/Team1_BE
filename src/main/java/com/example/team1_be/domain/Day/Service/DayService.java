package com.example.team1_be.domain.Day.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Week.Week;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayService {
	private final DayReadOnlyService dayReadOnlyService;
	private final DayWriteOnlyService dayWriteOnlyService;

	public void createDays(List<Day> days) {
		dayWriteOnlyService.createDays(days);
	}

	public List<Day> findByWeek(Week week) {
		return dayReadOnlyService.findByWeek(week);
	}
}
