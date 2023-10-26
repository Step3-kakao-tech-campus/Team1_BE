package com.example.team1_be.domain.Schedule.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleWriteService {
	private final ScheduleRepository scheduleRepository;

	public void createSchedule(Schedule schedule) {
		scheduleRepository.save(schedule);
	}
}
