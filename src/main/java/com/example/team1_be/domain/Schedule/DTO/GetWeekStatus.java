package com.example.team1_be.domain.Schedule.DTO;

import com.example.team1_be.domain.Week.WeekRecruitmentStatus;

import lombok.Getter;

public class GetWeekStatus {
	@Getter
	public static class Response {
		private String weekStatus;

		public Response(WeekRecruitmentStatus status) {
			if (status == null) {
				this.weekStatus = "allocatable";
			}
			if (status == WeekRecruitmentStatus.STARTED) {
				this.weekStatus = "inProgress";
			}
			if (status == WeekRecruitmentStatus.ENDED) {
				this.weekStatus = "closed";
			}
		}
	}
}
