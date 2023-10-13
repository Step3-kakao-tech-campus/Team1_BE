package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.util.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("그룹 생성하기 성공")
    @WithMockCustomUser
    @Sql("group-create1.sql")
    @Test
    void postCreate1() throws Exception {
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
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("그룹 생성하기 DTO 검증 실패(멤버변수 누락)")
    @WithMockCustomUser
    @Sql("group-create1.sql")
    @Test
    void postCreate2() throws Exception {
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
        perform.andDo(print());
    }

    @DisplayName("그룹 생성하기 실패(이미 가입된 그룹 존재)")
    @WithMockCustomUser
    @Sql("group-create2.sql")
    @Test
    void postCreate3() throws Exception {
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
        perform.andDo(print());
    }

    @DisplayName("그룹 초대장 제출 성공")
    @WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
    @Sql("group-invitationAccept1.sql")
    @Test
    void invitationAccept1() throws Exception {
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

    @DisplayName("그룹 초대장 제출 실패(초대장 갱신시점이 없음)")
    @WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
    @Sql("group-invitationAccept2.sql")
    @Test
    void invitationAccept2() throws Exception {
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
    void invitationAccept3() throws Exception {
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

    @DisplayName("그룹 초대장 DTO 실패(초대내용이 없음)")
    @WithMockCustomUser(username = "dksgkswn", userId = "2", kakaoId = "2")
    @Sql("group-invitationAccept1.sql")
    @Test
    void invitationAccept4() throws Exception {
        // given
        InvitationAccept.Request requestDTO = new InvitationAccept.Request();
        String request = om.writeValueAsString(requestDTO);

        // when
        ResultActions perform = mvc.perform(post("/group/invitation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        // then
        perform.andExpect(status().isBadRequest());
        perform.andDo(print());
    }
}
