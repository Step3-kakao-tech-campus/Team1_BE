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
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.team1_be.domain.Schedule.DTO.FixSchedule;
import com.example.team1_be.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
public class GetDailyFixedAppliesTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;

	@DisplayName("요청 성공")
	@WithMockCustomUser
	@Test
	public void test1() throws Exception {
		// given
		LocalDate date = LocalDate.parse("2023-10-16");
		mvc.perform(
				get(String.format("/schedule/recommend/%s", date)))
			.andExpect(status().isOk())
			.andDo(print());

		FixSchedule.Request requestDTO = new FixSchedule.Request(date, 1);
		String request = om.writeValueAsString(requestDTO);
		mvc.perform(
				post("/schedule/fix")
					.contentType(MediaType.APPLICATION_JSON)
					.content(request))
			.andExpect(status().isOk())
			.andDo(print());

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/fix/day/%s", date)));
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("요청 실패(잘못된 양식의 주소 요청")
	@WithMockCustomUser
	@Test
	public void test2() throws Exception {
		String wrongDateFormat = "2023-22";
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/fix/day/%s", wrongDateFormat)));
		perform.andExpect(status().isBadRequest());
	}
}
