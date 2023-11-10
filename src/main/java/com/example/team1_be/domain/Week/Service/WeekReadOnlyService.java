package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.team1_be.utils.errors.exception.CustomException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Week.WeekRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeekReadOnlyService {
	private final WeekRepository weekRepository;
	private final int LATEST_WEEK_LIMIT = 1;

	public Week findByGroupAndStartDate(Group group, LocalDate startDate) {
		return weekRepository.findByGroupIdAndStartDate(group.getId(), startDate).orElse(null);
	}

	public Week findByGroupAndStatus(Group group, WeekRecruitmentStatus status) {
		List<Week> weeks = weekRepository.findLatestByScheduleAndStatus(group.getId(), status,
			PageRequest.of(0, LATEST_WEEK_LIMIT)).getContent();
		if (weeks.isEmpty()) {
			throw new CustomException("최근 스케줄을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
		return weeks.get(0);
	}
}
