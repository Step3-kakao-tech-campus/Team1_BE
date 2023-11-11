package com.example.team1_be.domain.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.team1_be.domain.User.DTO.Join;
import com.example.team1_be.domain.User.DTO.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
class UserControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;

	@DisplayName("로그인 시 request body가 누락된 경우 테스트")
	@Test
	void shouldFailLoginDueToMissingRequestBody() throws Exception {
		Login.Request requestDTO = new Login.Request(null);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	@DisplayName("로그인하는 code가 만료되었거나 유효하지 않은 경우 테스트")
	@Test
	void shouldFailLoginDueToExpiredCode() throws Exception {
		Login.Request requestDTO = new Login.Request("nnnn");
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isInternalServerError());
		perform.andDo(print());
	}

	@DisplayName("회원가입 시, login을 거치지 않았으면 실패 테스트")
	@Sql("register.sql")
	@Test
	void shouldFailRegisterWithoutLoginTest() throws Exception {
		Join.Request requestDTO = new Join.Request("cccc", "jiwon", true);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/auth/join").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	@DisplayName("회원가입 시 request body가 누락된 경우 실패 테스트")
	@Sql("register.sql")
	@Test
	void shouldFailRegisterDueToMissingRequestBody() throws Exception {
		Join.Request requestDTO = new Join.Request("cccc", "jiwon", null);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/auth/join").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	@DisplayName("회원가입 시, 이미 같은 code로 가입한 경우 테스트")
	@Sql("register.sql")
	@Test
	void shouldFailRegisterDueToDuplicateCode() throws Exception {
		Join.Request requestDTO = new Join.Request("aaaa", "eunjin", true);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/auth/join").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	@DisplayName("회원가입 성공 테스트")
	@Sql("register.sql")
	@Test
	void shouldRegisterSuccessfully() throws Exception {
		Join.Request requestDTO = new Join.Request("bbbb", "dlwogns", true);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/api/auth/join").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isOk());
		perform.andDo(print());
	}
}