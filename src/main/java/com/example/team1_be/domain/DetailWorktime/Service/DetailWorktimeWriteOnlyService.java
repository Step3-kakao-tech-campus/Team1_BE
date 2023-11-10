package com.example.team1_be.domain.DetailWorktime.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void createDays(List<DetailWorktime> days) {
		logger.info("{}개의 상세 근무 시간 정보를 생성합니다.", days.size());
		repository.saveAll(days);
	}

	public void createDaysWithWorktimesAndAmounts(LocalDate startDate, List<Worktime> weeklyWorktimes,
		List<List<Long>> weeklyDemands) {
		List<List<DetailWorktime>> weeklyDetailWorktimes = new ArrayList<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			int weekdayOrdinal = day.ordinal();
			LocalDate date = startDate.plusDays(weekdayOrdinal);
			weeklyDetailWorktimes.add(createDailyDemands(date, weeklyWorktimes, weeklyDemands.get(weekdayOrdinal)));
		}
		List<DetailWorktime> falttenedDetailsWorktimes = flattenListOfListsImperatively(weeklyDetailWorktimes);
		logger.info("주간 근무 시간 정보를 생성합니다.");
		createDays(falttenedDetailsWorktimes);
	}

	private List<DetailWorktime> createDailyDemands(LocalDate date, List<Worktime> weeklyWorktimes,
		List<Long> dailyDemands) {
		List<DetailWorktime> dailyDetailWorktimes = new ArrayList<>();
		for (int i = 0; i < weeklyWorktimes.size(); i++) {
			dailyDetailWorktimes.add(createDayWithWorktimeAndAmount(date, weeklyWorktimes.get(i), dailyDemands.get(i)));
		}
		logger.info("{}에 대한 일간 근무 시간 정보를 생성합니다.", date);
		return dailyDetailWorktimes;
	}

	private DetailWorktime createDayWithWorktimeAndAmount(LocalDate date, Worktime worktime, Long dailyDemand) {
		logger.info("근무 시간 ID: {}, 요일: {}, 요청량: {}에 대한 상세 근무 시간 정보를 생성합니다.", worktime.getId(), date.getDayOfWeek(),
			dailyDemand);
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
