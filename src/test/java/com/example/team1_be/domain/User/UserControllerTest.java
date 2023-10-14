package com.example.team1_be.domain.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    private final String INVALID_KAKAO_TOKEN = "ThisIsInvalidToken";  // 12시간 경과 후 만료되거나, 잘못된 토큰
    // 토큰 새로 발급받고 변경 필수 : https://kauth.kakao.com/oauth/authorize?client_id=ba5bf7b3c440fb54f054ac5c3bfff761&redirect_uri=http://localhost:8080/login/kakao&response_type=code
    private final String NEW_KAKAO_TOKEN = "UB0XARcunXEccYxozP8ACj1LS37UiSh4owU4NV4qCj1zmwAAAYsvoZSs";

    @DisplayName("회원가입 성공")
    @Test
    void join_success_test() throws Exception {
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

    @DisplayName("회원가입 실패(토큰만료 및 유효하지않은 토큰)")
    @Test
    void join_token_expiration_test() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(INVALID_KAKAO_TOKEN)
                .build();
        String content = om.writeValueAsString(joinDTO);

        ResultActions perform = mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success_test() throws Exception {
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

    @DisplayName("로그인 실패(토큰만료 및 유효하지 않은 토큰)")
    @Test
    void login_token_expiration_test() throws Exception {
        UserRequest.JoinDTO joinDTO = UserRequest.JoinDTO.builder()
                .name("최은진")
                .accessToken(NEW_KAKAO_TOKEN)
                .build();
        String joinDTOContent = om.writeValueAsString(joinDTO);
        mvc.perform(post("/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(joinDTOContent));

        UserRequest.LoginDTO loginDTO = UserRequest.LoginDTO.builder()
                .accessToken(INVALID_KAKAO_TOKEN)
                .build();
        String loginDTOContent = om.writeValueAsString(loginDTO);
        ResultActions perform = mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDTOContent));

        perform.andExpect(status().isBadRequest());
    }
}