package com.example.team1_be.domain.Day.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayService {
	private final DayRepository dayRepository;

	@Transactional
	public void createDays(List<Day> days) {
		dayRepository.saveAll(days);
	}

	public List<Day> findByWeek(Week week) {
		List<Day> days = dayRepository.findByWeekId(week.getId());
		if (days.isEmpty()) {
			throw new CustomException("잘못된 요청입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return days;
	}
}
