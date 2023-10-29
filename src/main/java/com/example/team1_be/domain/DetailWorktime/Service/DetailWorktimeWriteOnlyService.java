package com.example.team1_be.domain.DetailWorktime.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.DetailWorktime.DetailWorktimeRepository;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DetailWorktimeWriteOnlyService {
	private final DetailWorktimeRepository repository;

	public List<DetailWorktime> createDays(List<DetailWorktime> days) {
		return repository.saveAll(days);
	}

	public List<DetailWorktime> createDaysWithWorktimesAndAmounts(LocalDate startDate, List<Worktime> weeklyWorktimes,
		List<List<Long>> weeklyDemands) {
		List<List<DetailWorktime>> weeklyDetailWorktimes = new ArrayList<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			int weekdayOrdinal = day.ordinal();
			LocalDate date = startDate.plusDays(weekdayOrdinal);
			weeklyDetailWorktimes.add(createDailyDemands(date, weeklyWorktimes, weeklyDemands.get(weekdayOrdinal)));
		}
		List<DetailWorktime> falttenedDetailsWorktimes = flattenListOfListsImperatively(weeklyDetailWorktimes);
		return createDays(falttenedDetailsWorktimes);
	}

	private List<DetailWorktime> createDailyDemands(LocalDate date, List<Worktime> weeklyWorktimes,
		List<Long> dailyDemands) {
		List<DetailWorktime> dailyDetailWorktimes = new ArrayList<>();
		for (int i = 0; i < weeklyWorktimes.size(); i++) {
			dailyDetailWorktimes.add(createDayWithWorktimeAndAmount(date, weeklyWorktimes.get(i), dailyDemands.get(i)));
		}
		return dailyDetailWorktimes;
	}

	private DetailWorktime createDayWithWorktimeAndAmount(LocalDate date, Worktime worktime, Long dailyDemand) {
		return DetailWorktime.builder()
			.date(date)
			.dayOfWeek(date.getDayOfWeek())
			.worktime(worktime)
			.amount(dailyDemand)
			.build();
	}

	public <T> List<T> flattenListOfListsImperatively(
		List<List<T>> nestedList) {
		List<T> ls = new ArrayList<>();
		nestedList.forEach(ls::addAll);
		return ls;
	}

}
