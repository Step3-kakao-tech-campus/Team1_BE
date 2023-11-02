package com.example.team1_be.domain.Schedule.DTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.team1_be.domain.Worktime.Worktime;

import lombok.Getter;

public class LoadLatestSchedule {
	@Getter
	public static class Response {
		private final List<Template> template;

		public Response(List<Worktime> lastestWorktimes) {
			template = new ArrayList<>();
			for (Worktime worktime : lastestWorktimes) {
				template.add(new Template(worktime));
			}
		}

		@Getter
		private class Template {
			private final String title;
			private final LocalTime startTime;
			private final LocalTime endTime;

			public Template(Worktime worktime) {
				this.title = worktime.getTitle();
				this.startTime = worktime.getStartTime();
				this.endTime = worktime.getEndTime();
			}
		}
	}
}
