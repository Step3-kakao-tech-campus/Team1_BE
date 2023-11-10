package com.example.team1_be.domain.Schedule.Recommend;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleGenerator {
	private final int GEN_LIMIT = 3;
	private final List<Worktime> worktimes;
	private final List<Apply> applyList;
	private final Map<Long, Long> requestMap;
	private final List<List<Apply>> generatedApplies;
	private int limit;
	private int index;
	private Map<Long, Long> remainRequestMap;
	private List<Apply> fixedApplies;

	public ScheduleGenerator(List<Worktime> worktimes, List<Apply> applyList, Map<Long, Long> requestMap) {
		this.worktimes = worktimes;
		this.applyList = applyList;
		this.requestMap = requestMap;
		this.generatedApplies = new ArrayList<>();
		this.limit = GEN_LIMIT;
		this.index = 0;
		this.remainRequestMap = new HashMap<>(this.requestMap);
		this.fixedApplies = new ArrayList<>();
	}

	public List<Map<DayOfWeek, SortedMap<Worktime, List<Apply>>>> generateSchedule() {
		log.info("스케줄을 생성합니다.");
		recursiveSearch();

		List<Map<DayOfWeek, SortedMap<Worktime, List<Apply>>>> result = this.generatedApplies.stream()
			.map(this::generateDayOfWeekSortedMap)
			.collect(Collectors.toList());

		log.info("스케줄 생성이 완료되었습니다.");
		return result;
	}

	private void recursiveSearch() {
		while (index < applyList.size() && limit > 0) {
			Apply selectedApply = applyList.get(index);
			Long selectedWorktimeId = selectedApply.getDetailWorktime().getId();
			Long selectedAppliers = remainRequestMap.get(selectedWorktimeId);

			if (selectedAppliers > 0) {
				remainRequestMap.put(selectedWorktimeId, selectedAppliers - 1);
				fixedApplies.add(selectedApply);
				index++;
				recursiveSearch();
				if (limit == 0) {
					return;
				}
			}
			index++;
		}

		if (isSearchComplete()) {
			generatedApplies.add(new ArrayList<>(fixedApplies));
			limit--;
		}
	}

	private boolean isSearchComplete() {
		return remainRequestMap.values().stream().mapToInt(Long::intValue).sum() == 0 || index == applyList.size();
	}

	private Map<DayOfWeek, SortedMap<Worktime, List<Apply>>> generateDayOfWeekSortedMap(List<Apply> applies) {
		Map<DayOfWeek, SortedMap<Worktime, List<Apply>>> recommend = new HashMap<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			SortedMap<Worktime, List<Apply>> appliesByWorktime = new TreeMap<>(
				Comparator.comparing(Worktime::getStartTime));
			List<Apply> appliesByDay = filterApplies(applies, day, null);

			for (Worktime worktime : this.worktimes) {
				List<Apply> appliesByDayAndWorktime = filterApplies(appliesByDay, day, worktime);
				if (!appliesByDayAndWorktime.isEmpty()) {
					Worktime key = appliesByDayAndWorktime.get(0).getDetailWorktime().getWorktime();
					appliesByWorktime.put(key, appliesByDayAndWorktime);
				}
			}
			recommend.put(day, appliesByWorktime);
		}
		return recommend;
	}

	private List<Apply> filterApplies(List<Apply> applies, DayOfWeek day, Worktime worktime) {
		return applies.stream()
			.filter(apply -> apply.getDetailWorktime().getDayOfWeek().equals(day) &&
				(worktime == null || apply.getDetailWorktime().getWorktime().getId().equals(worktime.getId())))
			.collect(Collectors.toList());
	}
}
