package com.example.team1_be.domain.Schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.team1_be.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
public class GetWeekStatus {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;

	@DisplayName("주별 상태 조회 closed")
	@WithMockCustomUser
	@Test
	void getWeekStatus1() throws Exception {
		// given
		LocalDate startDate = LocalDate.parse("2023-10-09");

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/status/%s", startDate)));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("주별 상태 조회 inProgress")
	@WithMockCustomUser
	@Test
	void getWeekStatus2() throws Exception {
		// given
		LocalDate startDate = LocalDate.parse("2023-10-16");

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/status/%s", startDate)));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("주별 상태 조회 allocatable")
	@WithMockCustomUser
	@Test
	void getWeekStatus3() throws Exception {
		// given
		LocalDate startDate = LocalDate.parse("2023-10-23");

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/status/%s", startDate)));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}
}
