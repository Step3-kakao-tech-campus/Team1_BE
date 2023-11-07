package com.example.team1_be.domain.DetailWorktime.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.DetailWorktime.DetailWorktimeRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailWorktimeReadOnlyService {
	private final DetailWorktimeRepository repository;

	public List<DetailWorktime> findByGroupAndDateAndStatus(Group group, LocalDate date, WeekRecruitmentStatus status) {
		return repository.findByGroupAndDatesAndStatus(group.getId(), date, status);
	}

	public List<Apply> findAppliesByWorktimeAndStatus(Worktime worktime, ApplyStatus status, DayOfWeek day) {
		DetailWorktime detailWorktime = repository.findByWorktimeAndStatus(worktime.getId(), day)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 세부 근무 일정입니다."));
		return detailWorktime.getApplies();
	}

	public List<DetailWorktime> findByStartDateAndGroup(LocalDate date, Group group) {
		List<DetailWorktime> detailWorktimes = repository.findByStartDateAndGroup(date, group.getId());
		if (detailWorktimes.isEmpty()) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_NOT_STARTED, HttpStatus.BAD_REQUEST);	// 등록된 근무일정이 없습니다.
		}
		return detailWorktimes;
	}

	public List<DetailWorktime> findByGroupAndDate(Group group, LocalDate selectedDate) {
		List<DetailWorktime> detailWorktimes = repository.findByGroupAndDate(group.getId(), selectedDate);
		if (detailWorktimes.isEmpty()) {
			throw new CustomException("등록된 근무일정이 없습니다.", HttpStatus.NOT_FOUND);
		}
		return detailWorktimes;
	}

	public List<DetailWorktime> findByStartDateAndWorktimes(LocalDate date, List<Long> ids) {
		List<DetailWorktime> detailWorktimes = repository.findByStartDateAndWorktimes(date, ids);
		return detailWorktimes;
	}

	public List<DetailWorktime> findByDayAndWorktimeIds(DayOfWeek day, List<Long> worktimeIds) {
		List<DetailWorktime> detailWorktimes = repository.findDayAndWorktimeIds(day, worktimeIds);
		if (detailWorktimes.isEmpty()) {
			throw new NotFoundException("Worktime은 생성되었지만 DetailWorktime이 생성되지 않은 논리적 오류입니다.");
		}
		return detailWorktimes;
	}
}
