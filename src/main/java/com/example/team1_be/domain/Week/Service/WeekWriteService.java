package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Week.WeekRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WeekWriteService {
	private final WeekRepository weekRepository;

	public Week createWeek(Group group, LocalDate weekStartDate) {
		Week week = Week.builder()
			.group(group)
			.status(WeekRecruitmentStatus.STARTED)
			.startDate(weekStartDate)
			.build();
		return weekRepository.save(week);
	}

	public void updateWeekStatus(Week week, WeekRecruitmentStatus weekRecruitmentStatus) {
		week.updateStatus(weekRecruitmentStatus);
		weekRepository.save(week);
	}
}
