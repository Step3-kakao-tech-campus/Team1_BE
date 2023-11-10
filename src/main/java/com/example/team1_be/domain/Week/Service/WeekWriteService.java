package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Week.WeekRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WeekWriteService {
	private final WeekRepository weekRepository;

	public Week createWeek(Group group, LocalDate weekStartDate) {
		log.info("새로운 주차를 생성합니다.");
		Week week = Week.builder()
			.group(group)
			.status(WeekRecruitmentStatus.STARTED)
			.startDate(weekStartDate)
			.build();
		Week savedWeek = weekRepository.save(week);
		log.info("새로운 주차가 생성되었습니다.");
		return savedWeek;
	}

	public void updateWeekStatus(Week week, WeekRecruitmentStatus weekRecruitmentStatus) {
		log.info("주차 상태를 업데이트합니다.");
		week.updateStatus(weekRecruitmentStatus);
		weekRepository.save(week);
		log.info("주차 상태 업데이트가 완료되었습니다.");
	}
}
