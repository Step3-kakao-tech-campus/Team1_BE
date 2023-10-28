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

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
public class GetAppliesTest {
	@Autowired
	private MockMvc mvc;

	@DisplayName("스케줄 신청/수정 조회 요청 성공")
	@WithMockCustomUser(userId = "2")
	@Test
	void test1() throws Exception {
		LocalDate startWeekDate = LocalDate.parse("2023-10-16");
		String URL = String.format("/schedule/application/%s", startWeekDate);
		ResultActions perform = mvc.perform(get(URL));
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}
}
