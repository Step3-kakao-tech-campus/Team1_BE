package com.example.team1_be.domain.Notification;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupCreateRequest;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Service.GroupService;
import com.example.team1_be.domain.Member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "/data.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class NotificationControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @DisplayName("해당 사용자의 전체 알림 조회")
    @WithUserDetails(value = "3", userDetailsServiceBeanName = "customUserDetailsService")
    public void find_all_notification_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/notification")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(jsonPath("$.response.notifications[0].date").value("2022-11-22T12:34:56"))
                .andExpect(jsonPath("$.response.notifications[0].content").value("ㅁㅁㅁ 님! 새로운 모임을 만들어보세요~"))
                .andExpect(jsonPath("$.response.notifications[0].notificationType").value("START"))
                .andExpect(jsonPath("$.response.notifications[1].date").value("2023-10-13T10:00"))
                .andExpect(jsonPath("$.response.notifications[1].content").value("ㅇㅇㅇ 님! 새로운 알림입니다."))
                .andExpect(jsonPath("$.response.notifications[1].notificationType").value("START"))

        ;
    }
}
