package com.example.team1_be.domain.Schedule.DTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;

public class GetDailyFixedApplies {
	@Getter
	public static class Response {
		private final List<DailyWorktimeApply> schedule;

		public Response(Map<Worktime, List<User>> dailyApplyMap) {
			this.schedule = new ArrayList<>();
			for (Worktime worktime : dailyApplyMap.keySet()) {
				this.schedule.add(new DailyWorktimeApply(worktime, dailyApplyMap.get(worktime)));
			}
		}

		@Getter
		private class DailyWorktimeApply {
			private final String title;
			private final LocalTime startTime;
			private final LocalTime endTime;
			private final List<Worker> workerList;

			public DailyWorktimeApply(Worktime worktime, List<User> applies) {
				this.title = worktime.getTitle();
				this.startTime = worktime.getStartTime();
				this.endTime = worktime.getEndTime();
				this.workerList = new ArrayList<>();
				applies.forEach(apply -> workerList.add(new Worker(apply)));
			}

			@Getter
			private class Worker {
				private final Long userId;
				private final String name;

				public Worker(User user) {
					this.userId = user.getId();
					this.name = user.getName();
				}
			}
		}
	}
}
