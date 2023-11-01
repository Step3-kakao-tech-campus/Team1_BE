package com.example.team1_be.domain.Notification;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.util.WithMockCustomUser;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @DisplayName("알림 전체 조회")
    @WithMockCustomUser
    @Sql("/data.sql")
    @Test
    void findAllNotification() throws Exception {
        // given

        // when
        ResultActions perform = mvc.perform(get("/notification")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk());
        perform.andDo(print());
    }


}