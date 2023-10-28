package com.example.team1_be.domain.Week.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WeekService {
	private final WeekReadOnlyService readOnlyService;
	private final WeekWriteService writeService;

	public Week createWeek(Group group, LocalDate weekStartDate) {
		return writeService.createWeek(group, weekStartDate);
	}

	public void updateWeekStatus(Week week, WeekRecruitmentStatus weekRecruitmentStatus) {
		writeService.updateWeekStatus(week, weekRecruitmentStatus);
	}

	public Week findByGroupAndStartDate(Group group, LocalDate startDate) {
		Week week = readOnlyService.findByGroupAndStartDate(group, startDate);
		if (null == week) {
			throw new NotFoundException("해당 주차를 찾을 수 없습니다.");
		}
		return week;
	}

	public List<Worktime> findWorktimes(Week week) {
		List<Worktime> worktimes = week.getWorktimes();
		if (null == worktimes) {
			throw new NotFoundException("근무일정이 존재하지 않습니다.");
		}
		return worktimes;
	}

	public Week findLatestByGroup(Group group) {
		return readOnlyService.findByGroupAndStatus(group, WeekRecruitmentStatus.ENDED);
	}

	public WeekRecruitmentStatus getWeekStatus(Group group, LocalDate startDate) {
		Week week = readOnlyService.findByGroupAndStartDate(group, startDate);
		if (null == week) {
			return null;
		} else {
			return week.getStatus();
		}
	}

	public void checkAppliable(User user, Week week) {
		if (user.getIsAdmin() && week.getStatus().equals(WeekRecruitmentStatus.ENDED)) {
			throw new NotFoundException("이미 마감된 스케줄입니다.");
		}
		if (!user.getIsAdmin() && week.getStatus().equals(WeekRecruitmentStatus.STARTED)) {
			throw new NotFoundException("확정된 스케줄이 아닙니다.");
		}
	}
}
