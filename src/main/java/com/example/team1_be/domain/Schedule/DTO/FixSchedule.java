package com.example.team1_be.domain.Schedule.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FixSchedule {
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		private LocalDate weekStartDate;
		private int selection;
	}
}
