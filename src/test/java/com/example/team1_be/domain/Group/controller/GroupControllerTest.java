package com.example.team1_be.domain.Group.controller;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupCreateRequest;
import com.example.team1_be.domain.Group.GroupRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "data.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GroupControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private GroupRepository groupRepository;


    @Test
    @DisplayName("그룹 생성 테스트")
    @WithUserDetails(value = "31", userDetailsServiceBeanName = "customUserDetailsService")
    public void group_create_test() throws Exception {
        // given
        GroupCreateRequest groupCreateRequest = new GroupCreateRequest("컴포즈커피 모덕점", "19", "011-0000-0019", "부산광역시", "모덕");
        String requestBody = om.writeValueAsString(groupCreateRequest);
        List<Group> groups = groupRepository.findAll();

        // when
        ResultActions resultActions = mvc.perform(
                post("/group")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isOk());

        assertThat(groupRepository.findAll().size()).isEqualTo(groups.size() + 1);
    }


    @Test
    @DisplayName("타 그룹 소속 유저의 그룹 생성 예외 테스트")
    @WithUserDetails(value = "3", userDetailsServiceBeanName = "customUserDetailsService")
    public void group_create_already_member_test() throws Exception {
        // given
        GroupCreateRequest groupCreateRequest = new GroupCreateRequest("컴포즈커피 모덕점", "19", "011-0000-0019", "부산광역시", "모덕");
        String requestBody = om.writeValueAsString(groupCreateRequest);
        List<Group> groups = groupRepository.findAll();

        // when
        ResultActions resultActions = mvc.perform(
                post("/group")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(status().isConflict());

        assertThat(groupRepository.findAll().size()).isEqualTo(groups.size());
    }
}