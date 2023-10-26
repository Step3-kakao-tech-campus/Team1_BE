package com.example.team1_be.domain.Schedule.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleReadOnlyService {
	private final ScheduleRepository scheduleRepository;

	public Schedule findByGroup(Group group) {
		return scheduleRepository.findByGroup(group)
			.orElseThrow(() -> new NotFoundException("스케줄을 찾을 수 없습니다."));
	}
}
