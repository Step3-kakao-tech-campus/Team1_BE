package com.example.team1_be.domain.Worktime.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.team1_be.utils.errors.ClientErrorCode;
import com.example.team1_be.utils.errors.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorktimeReadOnlyService {
	private final WorktimeRepository repository;

	public List<Worktime> findByGroupAndDate(Group group, LocalDate date) {
		log.info("그룹과 날짜에 따른 근무 시간을 찾습니다.");
		List<Worktime> worktimes = repository.findByDate(group.getId(), date);
		if (worktimes.isEmpty()) {
			log.info("해당 날짜에 등록된 스케줄이 없습니다.");
			throw new BadRequestException("해당 날짜에 등록된 스케줄이 없습니다.", ClientErrorCode.RECRUITMENT_NOT_STARTED);
		}
		log.info("그룹과 날짜에 따른 근무 시간을 찾았습니다.");
		return worktimes;
	}

	public List<Worktime> findByWeek(Week week) {
		log.info("주차에 따른 근무 시간을 찾습니다.");
		List<Worktime> worktimes = repository.findByWeek(week);
		if (worktimes.isEmpty()) {
			log.info("해당 날짜에 등록된 스케줄이 없습니다.");
			throw new NotFoundException("해당 날짜에 등록된 스케줄이 없습니다.");
		}
		log.info("주차에 따른 근무 시간을 찾았습니다.");
		return worktimes;
	}
}
