package com.example.team1_be.utils.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.util.WithMockCustomMemberUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationConfigTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;

	@DisplayName("XSS 보안 적용(특수문자)")
	@WithMockCustomMemberUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Test
	void XSSTest1() throws Exception {
		// given
		String XSSscript = "<script>alert(0);</script>";
		InvitationAccept.Request requestDTO = new InvitationAccept.Request(XSSscript);
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));
	}
}