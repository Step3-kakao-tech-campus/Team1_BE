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
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
class UserControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;

	@DisplayName("회원가입 테스트")
	@Sql("register.sql")
	@Test
	void test() throws Exception {
		Join.Request requestDTO = new Join.Request("abcd", "dlwogns", true);
		String request = om.writeValueAsString(requestDTO);
		ResultActions perform = mvc.perform(
			post("/auth/join").contentType(MediaType.APPLICATION_JSON).content(request));

		perform.andExpect(status().isOk());
		perform.andDo(print());
	}
}