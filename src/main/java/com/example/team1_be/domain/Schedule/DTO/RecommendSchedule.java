package com.example.team1_be.domain.Schedule.DTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;

public class RecommendSchedule {
	@Getter
	public static class Response {
		private final List<List<DailyWorkTimeList>> recommends;

		public Response(List<Worktime> weeklyWorktimes, List<List<Apply>> generatedSchedules) {
			this.recommends = new ArrayList<>();
			for (List<Apply> generatedSchedule : generatedSchedules) {
				List<DailyWorkTimeList> dailyWorkTimeLists = new ArrayList<>();
				for (Worktime worktime : weeklyWorktimes) {
					List<Apply> applicants = generatedSchedule.stream()
						.filter(x -> x.getWorktime().getId().equals(worktime.getId()))
						.collect(Collectors.toList());
					dailyWorkTimeLists.add(new DailyWorkTimeList(worktime, applicants));
				}
				this.recommends.add(dailyWorkTimeLists);
			}
		}

		@Getter
		private class DailyWorkTimeList {
			private final String title;
			private final LocalTime startTime;
			private final LocalTime endTime;
			private final List<Worker> workerList;

			public DailyWorkTimeList(Worktime worktime, List<Apply> applicants) {
				this.title = worktime.getTitle();
				this.startTime = worktime.getStartTime();
				this.endTime = worktime.getEndTime();
				this.workerList = applicants.stream().map(Worker::new).collect(Collectors.toList());
			}

			@Getter
			private class Worker {
				private final Long memberId;
				private final String name;

				public Worker(Apply apply) {
					this.memberId = apply.getUser().getId();
					this.name = apply.getUser().getName();
				}
			}
		}
	}
}
