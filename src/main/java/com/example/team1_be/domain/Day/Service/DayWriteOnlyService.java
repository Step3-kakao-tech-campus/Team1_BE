package com.example.team1_be.domain.Day.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DayWriteOnlyService {
	private final DayRepository dayRepository;

	public void createDays(List<Day> days) {
		dayRepository.saveAll(days);
	}
}
