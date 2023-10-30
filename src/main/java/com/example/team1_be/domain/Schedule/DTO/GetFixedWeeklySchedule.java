package com.example.team1_be.domain.Schedule.DTO;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;

public class GetFixedWeeklySchedule {
	@Getter
	public static class Response {
		private final List<DailySchedule> schedule;
		private final WorkSummary work_summary;

		public Response(SortedMap<LocalDate, List<Apply>> memberWorktimes) {
			this.schedule = new ArrayList<>();
			double totalWorktime = 0;
			for (LocalDate date : memberWorktimes.keySet()) {
				List<Apply> applies = memberWorktimes.get(date);
				System.out.println("apply size : " + applies.size());
				List<String> applyTitles = new ArrayList<>();

				for (Apply apply : applies) {
					Worktime worktime = apply.getDetailWorktime().getWorktime();
					applyTitles.add(worktime.getTitle());

					totalWorktime += Duration.between(worktime.getStartTime(), worktime.getEndTime()).toHours();
				}
				schedule.add(new DailySchedule(date, applyTitles));
			}
			this.work_summary = new WorkSummary(totalWorktime / memberWorktimes.size() * 7, totalWorktime);
		}

		@Getter
		private class WorkSummary {
			private final Double weekly;
			private final Double monthly;

			public WorkSummary(Double weekly, Double monthly) {
				this.weekly = weekly;
				this.monthly = monthly;
			}
		}

		@Getter
		private class DailySchedule {
			private final LocalDate date;
			private final List<String> workTime;

			public DailySchedule(LocalDate date, List<String> applies) {
				this.date = date;
				this.workTime = applies;
			}
		}
	}
}
