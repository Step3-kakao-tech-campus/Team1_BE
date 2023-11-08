package com.example.team1_be.domain.Group;

import static org.assertj.core.api.Assertions.*;
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

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper om;
	@Autowired
	private GroupRepository groupRepository;

	// POST /group

	@DisplayName("그룹 생성하기 성공")
	@WithMockCustomUser(username = "eunjin", isAdmin = "true")
	@Sql("group-create1.sql")
	@Test
	void create_success() throws Exception {
		// given
		Create.Request requestDTO = Create.Request.builder()
			.marketName("kakao")
			.marketNumber("10-31223442")
			.mainAddress("금강로 279번길 19")
			.detailAddress("ㅁㅁ건물 2층 ㅇㅇ상가")
			.build();
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
		assertThat(groupRepository.findAll().size()).isEqualTo(1);
	}

	@DisplayName("그룹 생성하기 시, 토큰 유효성 검사 실패")
	@Test
	void create_fail_21000() throws Exception {
//		// given
//		Create.Request requestDTO = Create.Request.builder()
//				.marketName("kakao")
//				.marketNumber("10-31223442")
//				.mainAddress("금강로 279번길 19")
//				.detailAddress("ㅁㅁ건물 2층 ㅇㅇ상가")
//				.build();
//		String request = om.writeValueAsString(requestDTO);
//
//		// when
//		ResultActions perform = mvc.perform(post("/group")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(request));
//
//		// then
//		perform.andExpect(status().isUnauthorized());
//		perform.andExpect(jsonPath("$.error.errorCode").value("-21000"));
//		perform.andDo(print());
	}

	@DisplayName("그룹 생성하기 DTO 검증 실패(멤버변수 누락)")
	@WithMockCustomUser
	@Sql("group-create1.sql")
	@Test
	void create_fail_10004() throws Exception {
		// given
		Create.Request requestDTO = Create.Request.builder()
			.build();
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-10004"));
		perform.andDo(print());
	}

	@DisplayName("그룹 생성하기 DTO 검증 실패(폼 입력값 형식 오류)")
	@WithMockCustomUser(username = "eunjin", isAdmin = "true")
	@Test
	void create_fail_10005() throws Exception {
		// given
//		Create.Request requestDTO = Create.Request.builder()
//				.marketName("1")
//				.marketNumber("10-31223442")
//				.mainAddress("금강로 279번길 19")
//				.detailAddress("ㅁㅁ건물 2층 ㅇㅇ상가")
//				.build();
//		String request = om.writeValueAsString(requestDTO);
//
//		// when
//		ResultActions perform = mvc.perform(post("/group")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(request));
//
//		// then
//		perform.andExpect(status().isBadRequest());
//		perform.andExpect(jsonPath("$.error.errorCode").value("-10005"));
//		perform.andDo(print());
	}

	@DisplayName("그룹 생성은 매니저만 가능")
	@WithMockCustomUser
	@Sql("group-create1.sql")
	@Test
	void create_fail_21004() throws Exception {
		// given
		Create.Request requestDTO = Create.Request.builder()
				.marketName("kakao")
				.marketNumber("10-31223442")
				.mainAddress("금강로 279번길 19")
				.detailAddress("ㅁㅁ건물 2층 ㅇㅇ상가")
				.build();
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));

		// then
		perform.andExpect(status().isForbidden());
		perform.andExpect(jsonPath("$.error.errorCode").value("-21004"));
		perform.andDo(print());
	}

	@DisplayName("그룹 생성하기 실패(이미 가입된 그룹 존재)")
	@WithMockCustomUser(isAdmin = "true")
	@Sql("group-create3.sql")
	@Test
	void create_fail_20001() throws Exception {
		// given
		Create.Request requestDTO = Create.Request.builder()
				.marketName("kakao")
				.marketNumber("10-31223442")
				.mainAddress("금강로 279번길 19")
				.detailAddress("ㅁㅁ건물 2층 ㅇㅇ상가")
				.build();
		String request = om.writeValueAsString(requestDTO);

		mvc.perform(post("/group")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));

		// when
		ResultActions perform = mvc.perform(post("/group")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-20001"));
		perform.andDo(print());
	}

	// GET /group

	@DisplayName("그룹원 조회 성공")
	@WithMockCustomUser
	@Sql("group-getMembers1.sql")
	@Test
	void getMembers_success_inGroup() throws Exception {
		// when
		ResultActions perform = mvc.perform(get("/group"));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("그룹원 조회 성공 - 가입된 그룹이 없는 경우에도")
	@WithMockCustomUser
	@Sql("group-getMembers2.sql")
	@Test
	void getMembers_success_notInGroup() throws Exception {
		// when
		ResultActions perform = mvc.perform(get("/group"));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("그룹원 조회 - 토큰 인증 실패")
	@WithMockCustomUser
	@Sql("group-getMembers2.sql")
	@Test
	void getMembers_fail_21000() throws Exception {
//		// when
//		ResultActions perform = mvc.perform(get("/group"));
//
//		// then
//		perform.andExpect(status().isUnauthorized());
//		perform.andExpect(jsonPath("$.error.errorCode").value("-21000"));
//		perform.andDo(print());

	}

	// GET /group/invitation

	@DisplayName("그룹 초대링크 발급 성공")
	@WithMockCustomUser(isAdmin = "true")
	@Sql("group-getInvitation1.sql")
	@Test
	void getInvitation_success() throws Exception {
		// when
		ResultActions perform = mvc.perform(get("/group/invitation"));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("그룹 초대링크 발급 실패(그룹장 아님)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-getInvitation3.sql")
	@Test
	void getInvitation_fail_21004() throws Exception {
		// when
		ResultActions perform = mvc.perform(get("/group/invitation"));

		// then
		perform.andExpect(status().isForbidden());
		perform.andExpect(jsonPath("$.error.errorCode").value("-21004"));
		perform.andDo(print());
	}

	@DisplayName("그룹 초대링크 발급 실패(그룹 미등록으로 멤버가 아님)")
	@WithMockCustomUser(isAdmin = "true")
	@Sql("group-getInvitation2.sql")
	@Test
	void getInvitation_fail_21001() throws Exception {
		// when
		ResultActions perform = mvc.perform(get("/group/invitation"));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-21001"));
		perform.andDo(print());
	}

	// POST /group/invitation

	@DisplayName("그룹 초대장 제출 성공")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationAccept1.sql")
	@Test
	void invitationAccept_success() throws Exception {
		// given
		InvitationAccept.Request requestDTO = new InvitationAccept.Request("testcode1");
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 DTO 실패(초대키가 없음)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationAccept1.sql")
	@Test
	void invitationAccept_fail_10004() throws Exception {
		// given
		InvitationAccept.Request requestDTO = new InvitationAccept.Request();
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-10004"));
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 수락 실패(알바생 아님)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2", isAdmin = "true")
	@Sql("group-invitationAccept1.sql")
	@Test
	void invitationAccept_fail_21005() throws Exception {
		// given
		InvitationAccept.Request requestDTO = new InvitationAccept.Request("testcode1");
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));

		// then
		perform.andExpect(status().isForbidden());
		perform.andExpect(jsonPath("$.error.errorCode").value("-21005"));
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 수락 실패(이미 그룹에 가입됨)")
	@WithMockCustomUser(userId = "2")
	@Sql("group-invitationAccept4.sql")
	@Test
	void invitationAccept_fail_20001() throws Exception {
		// given
		InvitationAccept.Request requestDTO = new InvitationAccept.Request("testcode1");
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-20001"));
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 제출 실패(초대장 갱신시점이 없음)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationAccept2.sql")
	@Test
	void invitationAccept_fail_another1() throws Exception {
		// given
		InvitationAccept.Request requestDTO = new InvitationAccept.Request("testcode1");
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		perform.andExpect(status().isForbidden());
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 제출 실패(초대장 갱신시점 만료)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationAccept3.sql")
	@Test
	void invitationAccept_fail_another2() throws Exception {
		// given
		InvitationAccept.Request requestDTO = new InvitationAccept.Request("testcode1");
		String request = om.writeValueAsString(requestDTO);

		// when
		ResultActions perform = mvc.perform(post("/group/invitation")
			.contentType(MediaType.APPLICATION_JSON)
			.content(request));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	// GET /group/invitation/information/<invitationKey>

	@DisplayName("그룹 초대장 확인 성공")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationCheck1.sql")
	@Test
	void invitationCheck_success() throws Exception {
		// given
		String invitationKey = "testcode1";

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/group/invitation/information/%s", invitationKey)));

		// then
		perform.andExpect(status().isOk());
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 확인 실패(존재하지 않는 초대장)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationCheck2.sql")
	@Test
	void invitationCheck_fail_20004() throws Exception {
		// given
		String invitationKey = "testcode1";

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/group/invitation/information/%s", invitationKey)));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andExpect(jsonPath("$.error.errorCode").value("-20004"));
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 확인 실패(초대장 만료)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationCheck3.sql")
	@Test
	void invitationCheck_fail_another1() throws Exception {
		// given
		String invitationKey = "testcode1";

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/group/invitation/information/%s", invitationKey)));

		// then
		perform.andExpect(status().isBadRequest());
		perform.andDo(print());
	}

	@DisplayName("그룹 초대장 확인 실패(초대장 미갱신)")
	@WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
	@Sql("group-invitationCheck4.sql")
	@Test
	void invitationCheck_fail_another2() throws Exception {
		// given
		String invitationKey = "testcode1";

		// when
		ResultActions perform = mvc.perform(
			get(String.format("/group/invitation/information/%s", invitationKey)));

		// then
		perform.andExpect(status().isForbidden());
		perform.andDo(print());
	}


}
