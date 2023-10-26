package com.example.team1_be.domain.Day;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayService {
	private final DayRepository dayRepository;

	@Transactional
	public List<Day> createDays(List<Day> days) {
		days = dayRepository.saveAll(days);
		return dayRepository.findAllById(days.stream().map(Day::getId).collect(Collectors.toList()));
	}

	public List<Day> findByWeek(Week week) {
		List<Day> days = dayRepository.findByWeekId(week.getId());
		if (days.isEmpty()) {
			throw new CustomException("잘못된 요청입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return days;
	}
}
