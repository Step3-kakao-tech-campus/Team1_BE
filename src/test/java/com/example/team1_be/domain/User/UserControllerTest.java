package com.example.team1_be.domain.User;

import com.example.team1_be.utils.errors.exception.Exception404;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    private final String EXPIRED_KAKAO_TOKEN = "U9fuJNXvhMvuuRY3ZhW2heGCfAk423eQc-su0hQGCj1zTgAAAYsVW77c";  // 12시간 경과 후 만료된 토큰
    // 토큰 새로 발급받고 변경 필수 : https://kauth.kakao.com/oauth/authorize?client_id=ba5bf7b3c440fb54f054ac5c3bfff761&redirect_uri=http://localhost:8080/login/kakao&response_type=code
    private final String NEW_KAKAO_TOKEN = "U9fuJNXvhMvuuRY3ZhW2heGCfAk423eQc-su0hQGCj1zTgAAAYsVW77c";

    @DisplayName("토큰기반 회원가입 성공")
    @Test
    void test1() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(NEW_KAKAO_TOKEN)
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

    @DisplayName("토큰기반 회원가입 실패(토큰만료)")
    @Test
    void join_test_token_expiration() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(EXPIRED_KAKAO_TOKEN)
                .build();
        String content = om.writeValueAsString(joinDTO);

        assertThatThrownBy(() -> mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)))
                .isInstanceOf(Exception404.class);
    }

    @DisplayName("토큰기반 로그인 성공")
    @Test
    void test3() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(NEW_KAKAO_TOKEN)
                .build();
        String joinDTOContent = om.writeValueAsString(joinDTO);
        mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(joinDTOContent));

        UserRequest.LoginDTO loginDTO = UserRequest.LoginDTO.builder()
                .accessToken(NEW_KAKAO_TOKEN)
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
                .accessToken(NEW_KAKAO_TOKEN)
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

    @DisplayName("토큰기반 로그인 실패(토큰만료)")
    @Test
    void login_test_token_expiration() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(NEW_KAKAO_TOKEN)
                .build();
        String joinDTOContent = om.writeValueAsString(joinDTO);
        mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(joinDTOContent));

        UserRequest.LoginDTO loginDTO = UserRequest.LoginDTO.builder()
                .accessToken(EXPIRED_KAKAO_TOKEN)
                .build();
        String loginDTOContent = om.writeValueAsString(loginDTO);
        assertThatThrownBy(() -> mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDTOContent)))
                .isInstanceOf(Exception404.class);
    }
}