package com.example.team1_be.domain.Schedule.DTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;

public class RecommendSchedule {
	@Getter
	public static class Response {
		private final List<List<List<DailyWorkTimeList>>> recommends;

		public Response(List<Map<DayOfWeek, SortedMap<Worktime, List<Apply>>>> generatedSchedules) {
			this.recommends = new ArrayList<>();

			for (Map<DayOfWeek, SortedMap<Worktime, List<Apply>>> generatedSchedule : generatedSchedules) {
				List<List<DailyWorkTimeList>> weeklyWorkTimeLists = new ArrayList<>();
				for (DayOfWeek day : DayOfWeek.values()) {
					List<DailyWorkTimeList> dailyWorkTimeLists = new ArrayList<>();
					SortedMap<Worktime, List<Apply>> appliesByWorktime = generatedSchedule.get(day);
					for (Worktime worktime : appliesByWorktime.keySet()) {
						dailyWorkTimeLists.add(new DailyWorkTimeList(worktime, appliesByWorktime.get(worktime)));
					}
					weeklyWorkTimeLists.add(dailyWorkTimeLists);
				}

				this.recommends.add(weeklyWorkTimeLists);
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
				this.workerList = applicants.stream().map(Apply::getUser).map(Worker::new).collect(Collectors.toList());
			}

			@Getter
			private class Worker {
				private final Long memberId;
				private final String name;

				public Worker(User user) {
					this.memberId = user.getId();
					this.name = user.getName();
				}
			}
		}
	}
}
