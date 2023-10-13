package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("group-data.sql")
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
    @Test
    void postCreate2() throws Exception {
        // given
        Create.Request requestDTO = Create.Request.builder()
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
        perform.andExpect(status().isBadRequest());
        perform.andDo(print());
    }
}
