package com.example.team1_be.domain.DetailWorktime.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.team1_be.utils.errors.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DetailWorktimeService {
	private final DetailWorktimeReadOnlyService readOnlyService;
	private final DetailWorktimeWriteOnlyService writeOnlyService;

	public void createDays(List<DetailWorktime> days) {
		writeOnlyService.createDays(days);
	}

	public List<DetailWorktime> createDays(LocalDate week, List<Worktime> weeklyWorktimes, List<List<Long>> amount) {
		return writeOnlyService.createDaysWithWorktimesAndAmounts(week, weeklyWorktimes, amount);
	}

	public Map<String, List<Map<Worktime, List<Apply>>>> findAppliesByWorktimeAndDayAndStatus(List<Worktime> worktimes,
		ApplyStatus status) {
		Map<String, List<Map<Worktime, List<Apply>>>> weeklyApplies = new HashMap<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			List<Map<Worktime, List<Apply>>> dailyApplies = new ArrayList<>();
			for (Worktime worktime : worktimes) {
				Map<Worktime, List<Apply>> appliesByWorktime = new HashMap<>();
				List<Apply> applies = readOnlyService.findAppliesByWorktimeAndStatus(worktime, status, day);
				appliesByWorktime.put(worktime, applies);
				dailyApplies.add(appliesByWorktime);
			}
			weeklyApplies.put(day.toString(), dailyApplies);
		}
		return weeklyApplies;
	}

	public SortedMap<LocalDate, List<DetailWorktime>> findEndedByGroupAndYearMonth(Group group, YearMonth yearMonth) {
		LocalDate start = yearMonth.atDay(1);
		LocalDate end = yearMonth.atEndOfMonth();

		SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailsWorktimesMap = new TreeMap<>(
			(s1, s2) -> s1.compareTo(s2));
		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
			List<DetailWorktime> detailWorktimes = readOnlyService.findByGroupAndDateAndStatus(group, date,
				WeekRecruitmentStatus.ENDED);

			if (detailWorktimes.isEmpty()) {
				continue;
			}

			monthlyDetailsWorktimesMap.put(date, detailWorktimes);
		}

		if (monthlyDetailsWorktimesMap.isEmpty()) {
			throw new CustomException("확정된 스케줄이 없습니다.", HttpStatus.NOT_FOUND);
		}
		return monthlyDetailsWorktimesMap;
	}

	public List<DetailWorktime> findByStartDateAndGroup(LocalDate date, Group group) {
		return readOnlyService.findByStartDateAndGroup(date, group);
	}

	public List<DetailWorktime> findByGroupAndDate(Group group, LocalDate selectedDate) {
		return readOnlyService.findByGroupAndDate(group, selectedDate);
	}

	public List<DetailWorktime> findByStartDateAndWorktimes(LocalDate date, List<Worktime> worktimes) {
		List<Long> ids = worktimes.stream().map(Worktime::getId).collect(
			Collectors.toList());
		return readOnlyService.findByStartDateAndWorktimes(date, ids);
	}

	public List<DetailWorktime> findByStartDateAndWorktimes(SortedMap<DayOfWeek, List<Worktime>> weeklyApplies) {
		List<DetailWorktime> appliedDetailWorktimes = new ArrayList<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			List<Long> worktimeIds = weeklyApplies.get(day).stream().map(Worktime::getId).collect(Collectors.toList());
			if (!worktimeIds.isEmpty()) {
				appliedDetailWorktimes.addAll(readOnlyService.findByDayAndWorktimeIds(day, worktimeIds));
			}
		}
		return appliedDetailWorktimes;
	}
}
