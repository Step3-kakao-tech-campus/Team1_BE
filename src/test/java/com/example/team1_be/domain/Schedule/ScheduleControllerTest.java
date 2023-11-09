package com.example.team1_be.domain.Schedule;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.YearMonth;

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
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklyScheduleRepository;
import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApplyRepository;
import com.example.team1_be.util.WithMockCustomAdminUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
class ScheduleControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;
	@Autowired
	private RecommendedWorktimeApplyRepository recommendedWorktimeApplyRepository;
	@Autowired
	private RecommendedWeeklyScheduleRepository recommendedWeeklyScheduleRepository;

	@DisplayName("주별 스케줄 신청 현황 조회 실패(매니저-모집중아님)")
	@WithMockCustomAdminUser(isAdmin = "true")
	@Test
	void weeklyScheduleCheck1() throws Exception {
		LocalDate startWeekDate = LocalDate.parse("2023-10-09");
		ResultActions perform = mvc.perform(get(String.format("/api/schedule/remain/week/%s", startWeekDate)));

		perform.andExpect(status().isNotFound());
		perform.andDo(print());
	}

	@DisplayName("주별 스케줄 신청 현황 조회 실패(잘못된 날짜 양식)")
	@WithMockCustomAdminUser
	@Test
	void weeklyScheduleCheck2() throws Exception {
		String wrongDate = "10-10";
		ResultActions perform = mvc.perform(get(String.format("/api/schedule/remain/week/%s", wrongDate)));

		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	@DisplayName("주별 스케줄 신청 현황 조회 실패(매니저 시작한 주 신청아님)")
	@WithMockCustomAdminUser(isAdmin = "true")
	@Test
	void weeklyScheduleCheck3() throws Exception {
		LocalDate startWeekDate = LocalDate.parse("2023-10-09");
		ResultActions perform = mvc.perform(get(String.format("/api/schedule/remain/week/%s", startWeekDate)));

		perform.andExpect(status().isNotFound());
	}

	@DisplayName("주별 스케줄 신청 현황 조회 실패(알바생 마감한 주 신청아님)")
	@WithMockCustomAdminUser(userId = "2")
	@Test
	void weeklyScheduleCheck4() throws Exception {
		LocalDate startWeekDate = LocalDate.parse("2023-10-16");
		ResultActions perform = mvc.perform(get(String.format("/api/schedule/remain/week/%s", startWeekDate)));

		perform.andExpect(status().isNotFound());
	}

	@DisplayName("주별 스케줄 신청 현황 조회 성공(매니저)")
	@WithMockCustomAdminUser(isAdmin = "true")
	@Test
	void weeklyScheduleCheck5() throws Exception {
		LocalDate startWeekDate = LocalDate.parse("2023-10-16");
		ResultActions perform = mvc.perform(get(String.format("/api/schedule/remain/week/%s", startWeekDate)));

		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("주별 스케줄 신청 현황 조회 성공(알바생)")
	@WithMockCustomAdminUser(userId = "2")
	@Test
	void weeklyScheduleCheck7() throws Exception {
		LocalDate startWeekDate = LocalDate.parse("2023-10-09");
		ResultActions perform = mvc.perform(get(String.format("/api/schedule/remain/week/%s", startWeekDate)));

		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	// GET /schedule/fix/month

	@DisplayName("멤버별 확정 스케줄 조회 성공")
	@WithMockCustomAdminUser
	@Test
	void getFixedWeeklySchedule_success() throws Exception {
		YearMonth month = YearMonth.parse("2023-10");
		Long memberId = 2L;
		ResultActions perform = mvc.perform(
			get(String.format("/api/schedule/fix/month/%s/%s", month, memberId)));
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("멤버별 확정 스케줄 조회 - 토큰 유효성 실패")
	@Test
	void getFixedWeeklySchedule_fail_21000() throws Exception {
		YearMonth month = YearMonth.parse("2023-10");
		Long memberId = 2L;
		ResultActions perform = mvc.perform(
				get(String.format("/api/schedule/fix/month/%s/%s", month, memberId)));
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-21000"));
		perform.andDo(print());
	}

	@DisplayName("멤버별 확정 스케줄 조회 실패(파라미터 에러)")
	@WithMockCustomAdminUser
	@Test
	void getFixedWeeklySchedule_fail_another_10002() throws Exception {
		Long memberId = 2L;
		ResultActions perform = mvc.perform(
			get(String.format("/api/schedule/fix/month/%s/%s", "2023", memberId)));
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-10003"));
		perform.andDo(print());
	}

	@DisplayName("추천 스케줄 후보 리스팅")
	@WithMockCustomAdminUser
	@Test
	void recommendSchedule1() throws Exception {
		LocalDate date = LocalDate.parse("2023-10-09");
		ResultActions perform = mvc.perform(
			get(String.format("/api/schedule/recommend/%s", date)));
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("스케줄 확정하기 성공")
	@WithMockCustomAdminUser
	@Test
	void fixSchedule1() throws Exception {
		// given
		LocalDate date = LocalDate.parse("2023-10-16");
		mvc.perform(
			get(String.format("/api/schedule/recommend/%s", date)));

		// when
		FixSchedule.Request requestDTO = new FixSchedule.Request(date, 1);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/schedule/fix")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)
		);
		perform.andExpect(status().isOk());
		assertThat(recommendedWeeklyScheduleRepository.findAll().size()).isEqualTo(0);
		assertThat(recommendedWorktimeApplyRepository.findAll().size()).isEqualTo(0);
		perform.andDo(print());
	}
}