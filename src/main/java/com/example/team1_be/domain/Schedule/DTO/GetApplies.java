package com.example.team1_be.domain.Schedule.DTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;

public class GetApplies {

	@Getter
	public static class Response {
		private List<Template> template;
		private List<List<SelectedStatus>> selected;

		public Response(List<Worktime> weeklyWorktimes, List<SortedMap<Worktime, Apply>> weeklyApplies) {
			this.template = weeklyWorktimes.stream().map(Template::new).collect(Collectors.toList());
			this.selected = new ArrayList<>();
			for (SortedMap<Worktime, Apply> dailyApplies : weeklyApplies) {
				List<SelectedStatus> dailySelectedStatus = new ArrayList<>();
				for (Worktime worktime : weeklyWorktimes) {
					dailySelectedStatus.add(new SelectedStatus(worktime, dailyApplies.get(worktime)));
				}
				this.selected.add(dailySelectedStatus);
			}
		}

		@Getter
		private class Template {
			private String title;
			private Long workTimeId;
			private LocalTime startTime;
			private LocalTime endTime;

			public Template(Worktime worktime) {
				this.title = title;
				this.workTimeId = workTimeId;
				this.startTime = startTime;
				this.endTime = endTime;
			}
		}

		@Getter
		private class SelectedStatus {
			private Long workTimeId;
			private Boolean isChecked;

			public SelectedStatus(Worktime worktime, Apply apply) {
				this.workTimeId = worktime.getId();
				this.isChecked = apply == null ? false : true;
			}
		}
	}
}
