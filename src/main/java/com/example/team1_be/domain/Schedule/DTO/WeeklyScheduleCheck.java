package com.example.team1_be.domain.Schedule.DTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class WeeklyScheduleCheck {
	@Getter
	@NoArgsConstructor
	public static class Request {
		@NotBlank
		private String weekStartDate;
	}

	@Getter
	@NoArgsConstructor
	public static class Response {
		private List<Template> template;
		private List<List<WorkerStatus>> applyStatus;

		public Response(List<Worktime> worktimes, Map<String, List<Map<Worktime, List<Apply>>>> applyStatus) {
			this.template = new ArrayList<>();
			worktimes.forEach(worktime -> template.add(new Template(worktime)));

			this.applyStatus = new ArrayList<>();
			for (String date : applyStatus.keySet()) {
				List<WorkerStatus> dailyWorkerStatuses = new ArrayList<>();
				for (Map<Worktime, List<Apply>> worktimeUsers : applyStatus.get(date)) {
					for (Worktime worktime : worktimeUsers.keySet()) {
						dailyWorkerStatuses.add(new WorkerStatus(worktime, worktimeUsers.get(worktime)));
					}
				}
				this.applyStatus.add(dailyWorkerStatuses);
			}
		}

		@Getter
		private class Template {
			private final Long worktimeId;
			private final String title;
			private final LocalTime startTime;
			private final LocalTime endTime;

			public Template(Worktime worktime) {
				this.worktimeId = worktime.getId();
				this.title = worktime.getTitle();
				this.startTime = worktime.getStartTime();
				this.endTime = worktime.getEndTime();
			}
		}

		@Getter
		private class WorkerStatus {
			private final Long worktimeId;
			private final List<Worker> workerList;

			public WorkerStatus(Worktime worktime, List<Apply> applies) {
				this.worktimeId = worktime.getId();
				this.workerList = applies.stream()
					.map(apply -> new Worker(apply.getUser()))
					.collect(Collectors.toList());
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
