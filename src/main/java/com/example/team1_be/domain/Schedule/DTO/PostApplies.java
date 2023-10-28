package com.example.team1_be.domain.Schedule.DTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostApplies {

	@Getter
	@NoArgsConstructor
	public static class Request {
		private LocalDate weekStartDate;
		private List<List<SelectedStatus>> apply;

		public Request(LocalDate weekStartDate, List<SortedMap<Worktime, Boolean>> weeklyAppliesByDay) {
			this.weekStartDate = weekStartDate;
			this.apply = new ArrayList<>();
			for (SortedMap<Worktime, Boolean> dailyApplies : weeklyAppliesByDay) {
				List<SelectedStatus> dailySelectedStatus = new ArrayList<>();
				for (Worktime worktime : dailyApplies.keySet()) {
					dailySelectedStatus.add(new SelectedStatus(worktime.getId(), dailyApplies.get(worktime)));
				}
				this.apply.add(dailySelectedStatus);
			}
		}

		@Getter
		@NoArgsConstructor
		public static class SelectedStatus {
			private Long workTimeId;
			private Boolean isChecked;

			public SelectedStatus(Long workTimeId, Boolean isChecked) {
				this.workTimeId = workTimeId;
				this.isChecked = isChecked;
			}
		}

		public SortedMap<DayOfWeek, SortedMap<>>
	}
}
