package com.example.team1_be.domain.Schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.YearMonth;

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
public class GetUsersFixedWeeklyScheduleTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;

	@DisplayName("개인 확정 스케줄 조회 성공")
	@WithMockCustomUser(userId = "2")
	@Test
	void GetUsersFixedWeeklySchedule1() throws Exception {
		YearMonth month = YearMonth.parse("2023-10");
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/fix/month/%s", month)));
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("개인 확정 스케줄 조회 실패(파라미터 에러)")
	@WithMockCustomUser(userId = "2")
	@Test
	void GetUsersFixedWeeklySchedule2() throws Exception {
		ResultActions perform = mvc.perform(
			get(String.format("/schedule/fix/month/%s", "2023")));
		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}
}
