package com.example.team1_be.domain.Schedule.Recommend;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;

public class SchduleGenerator {
	private final int GEN_LIMIT = 3;
	private final List<Worktime> worktimes;
	private final List<Apply> applyList;
	private final Map<Long, Long> requestMap;
	private final List<List<Apply>> generatedApplies;
	private int limit;

	public SchduleGenerator(List<Worktime> worktimes, List<Apply> applyList, Map<Long, Long> requestMap) {
		this.worktimes = worktimes;
		this.applyList = applyList;
		this.requestMap = requestMap;
		this.generatedApplies = new ArrayList<>();
		this.limit = GEN_LIMIT;
	}

	public List<Map<DayOfWeek, SortedMap<Worktime, List<Apply>>>> generateSchedule() {
		recursiveSearch(this.applyList, 0, this.requestMap, new ArrayList<>());

		List<Map<DayOfWeek, SortedMap<Worktime, List<Apply>>>> result = new ArrayList<>();
		for (List<Apply> applies : this.generatedApplies) {
			Map<DayOfWeek, SortedMap<Worktime, List<Apply>>> recommend = new HashMap<>();
			for (DayOfWeek day : DayOfWeek.values()) {
				SortedMap<Worktime, List<Apply>> appliesByWorktime = new TreeMap<>(
					(s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime()));
				List<Apply> appliesByDay = applies.stream()
					.filter(apply -> apply.getDetailWorktime().getDayOfWeek().equals(day))
					.collect(Collectors.toList());
				for (Worktime worktime : this.worktimes) {
					List<Apply> appliesByDayAndWorktime = appliesByDay.stream()
						.filter(apply -> apply.getDetailWorktime().getWorktime().getId().equals(worktime.getId()))
						.collect(Collectors.toList());
					if (appliesByDayAndWorktime.isEmpty()) {
						continue;
					}
					appliesByWorktime.put(worktime, appliesByDayAndWorktime);
				}
				recommend.put(day, appliesByWorktime);
			}
			result.add(recommend);
		}
		return result;
	}

	private void recursiveSearch(List<Apply> applyList, int index, Map<Long, Long> remainRequestMap,
		List<Apply> fixedApplies) {
		if (remainRequestMap.values().stream().mapToInt(Long::intValue).sum() == 0 || index == applyList.size()) {
			if (limit != 0) {
				this.generatedApplies.add(new ArrayList<>(fixedApplies));
				limit--;
			}
			return;
		}

		while (index < applyList.size()) {
			Map<Long, Long> copiedRequestMap = new HashMap<>(remainRequestMap);
			List<Apply> copiedFixedApplies = new ArrayList<>(fixedApplies);

			Apply selectedApply = applyList.get(index);
			Long selectedWorktimeId = selectedApply.getDetailWorktime().getId();
			Long selectedAppliers = copiedRequestMap.get(selectedWorktimeId);

			if (selectedAppliers != null && selectedAppliers != 0) {
				copiedRequestMap.put(selectedWorktimeId, selectedAppliers - 1);
				copiedFixedApplies.add(selectedApply);
				recursiveSearch(applyList, index + 1, copiedRequestMap, copiedFixedApplies);
				if (limit == 0) {
					return;
				}
			}

			index++;
		}
	}
}
