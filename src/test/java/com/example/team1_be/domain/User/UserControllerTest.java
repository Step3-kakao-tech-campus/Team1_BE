package com.example.team1_be.domain.User;

import com.example.team1_be.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.NestedServletException;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    private final String KAKAO_TOKEN = "U9fuJNXvhMvuuRY3ZhW2heGCfAk423eQc-su0hQGCj1zTgAAAYsVW77c";

    @DisplayName("토큰기반 회원가입 성공")
    @Test
    void test1() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(KAKAO_TOKEN)
                .build();
        String content = om.writeValueAsString(joinDTO);

        ResultActions perform = mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        perform.andExpect(status().isOk());
    }

    @DisplayName("토큰기반 회원가입 실패(토큰분실)")
    @Test
    void test2() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .build();
        String content = om.writeValueAsString(joinDTO);

        assertThatThrownBy(() -> mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)))
                .isInstanceOf(NestedServletException.class);
    }

    @DisplayName("토큰기반 로그인 성공")
    @Test
    void test3() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(KAKAO_TOKEN)
                .build();
        String joinDTOContent = om.writeValueAsString(joinDTO);
        mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(joinDTOContent));

        UserRequest.LoginDTO loginDTO = UserRequest.LoginDTO.builder()
                .accessToken(KAKAO_TOKEN)
                .build();
        String loginDTOContent = om.writeValueAsString(loginDTO);
        ResultActions perform = mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDTOContent));

        perform.andExpect(status().isOk());
    }

    @DisplayName("토큰기반 로그인 실패(토큰 분실)")
    @Test
    void test4() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(KAKAO_TOKEN)
                .build();
        String joinDTOContent = om.writeValueAsString(joinDTO);
        mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(joinDTOContent));

        UserRequest.LoginDTO loginDTO = UserRequest.LoginDTO.builder()
                .build();
        String loginDTOContent = om.writeValueAsString(loginDTO);
        assertThatThrownBy(() -> mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDTOContent)))
                .isInstanceOf(NestedServletException.class);
    }
}