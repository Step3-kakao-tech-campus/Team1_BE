package com.example.team1_be.domain.Schedule.DTO;

import static java.util.stream.Collectors.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
		private List<List<ApplyStatus>> applyStatus;

		public Response(List<List<Worktime>> weeklyWorktime, List<List<List<Apply>>> applyList) {
			this.applyStatus = new ArrayList<>();
			IntStream.range(0, weeklyWorktime.size()).forEach(
				(weeklyIdx) -> {
					List<Worktime> dailyWorktime = weeklyWorktime.get(weeklyIdx);
					List<List<Apply>> dailyApply = applyList.get(weeklyIdx);
					List<ApplyStatus> dailyApplyStatusList = new ArrayList<>();
					IntStream.range(0, dailyWorktime.size()).forEach(
						(dailyIndex) -> {
							dailyApplyStatusList.add(
								new ApplyStatus(dailyWorktime.get(dailyIndex), dailyApply.get(dailyIndex)));
						}
					);
					applyStatus.add(dailyApplyStatusList);
				}
			);
		}

		@Getter
		public static class ApplyStatus {
			private final String title;
			private final LocalTime startTime;
			private final LocalTime endTime;
			private final List<Worker> workerList;

			public ApplyStatus(Worktime worktimeList, List<Apply> applyList) {
				this.title = worktimeList.getTitle();
				this.startTime = worktimeList.getStartTime();
				this.endTime = worktimeList.getEndTime();
				this.workerList = applyList.stream()
					.map(Apply::getUser)
					.map(Worker::new)
					.collect(toList());
			}
		}

		@Getter
		public static class Worker {
			private final Long userId;
			private final String name;

			public Worker(User user) {
				this.userId = user.getId();
				this.name = user.getName();
			}
		}
	}
}
