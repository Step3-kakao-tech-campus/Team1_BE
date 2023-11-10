package com.example.team1_be.domain.Worktime.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WorktimeService {
	private final WorktimeReadOnlyService readOnlyService;
	private final WokrtimeWriteOnlyService writeOnlyService;

	public List<Worktime> createWorktimes(List<Worktime> worktimes) {
		log.info("근무 시간을 생성합니다.");
		List<Worktime> savedWorktimes = writeOnlyService.createWorktimes(worktimes);
		log.info("근무 시간 생성이 완료되었습니다.");
		return savedWorktimes;
	}

	public List<Worktime> createWorktimes(Week week, List<Worktime> weeklyWorktimes) {
		log.info("주차에 따른 근무 시간을 생성합니다.");
		weeklyWorktimes.forEach(worktime -> worktime.updateWeek(week));
		List<Worktime> savedWorktimes = createWorktimes(weeklyWorktimes);
		log.info("주차에 따른 근무 시간 생성이 완료되었습니다.");
		return savedWorktimes;
	}

	public List<Worktime> findByGroupAndDate(Group group, LocalDate date) {
		log.info("그룹과 날짜에 따른 근무 시간을 찾습니다.");
		List<Worktime> worktimes = readOnlyService.findByGroupAndDate(group, date);
		log.info("그룹과 날짜에 따른 근무 시간을 찾았습니다.");
		return worktimes;
	}

	public List<Worktime> findByWeekAndDate(Week week) {
		log.info("주차에 따른 근무 시간을 찾습니다.");
		List<Worktime> worktimes = readOnlyService.findByWeek(week);
		log.info("주차에 따른 근무 시간을 찾았습니다.");
		return worktimes;
	}

}
