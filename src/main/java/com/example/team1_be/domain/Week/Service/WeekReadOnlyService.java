package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeekReadOnlyService {
	private final WeekRepository weekRepository;
	private final int LATEST_WEEK_LIMIT = 1;

	public Week findByGroupAndStartDate(Group group, LocalDate startDate) {
		log.info("그룹과 시작 날짜에 따른 주차 정보를 찾습니다.");
		Week week = weekRepository.findByGroupIdAndStartDate(group.getId(), startDate).orElse(null);
		if (week == null) {
			log.info("해당 그룹과 시작 날짜에 해당하는 주차 정보를 찾을 수 없습니다.");
		} else {
			log.info("해당 그룹과 시작 날짜에 해당하는 주차 정보를 찾았습니다.");
		}
		return week;
	}

	public Week findByGroupAndStatus(Group group, WeekRecruitmentStatus status) {
		log.info("그룹과 상태에 따른 최근 주차 정보를 찾습니다.");
		List<Week> weeks = weekRepository.findLatestByScheduleAndStatus(group.getId(), status,
			PageRequest.of(0, LATEST_WEEK_LIMIT)).getContent();
		if (weeks.isEmpty()) {
			log.info("해당 그룹과 상태에 해당하는 최근 주차 정보를 찾을 수 없습니다.");
			throw new NotFoundException("최근 스케줄을 찾을 수 없습니다.");
		}
		log.info("해당 그룹과 상태에 해당하는 최근 주차 정보를 찾았습니다.");
		return weeks.get(0);
	}
}
