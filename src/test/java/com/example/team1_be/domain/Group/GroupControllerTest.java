package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupCreateRequest;
import com.example.team1_be.domain.Group.GroupMemberListResponse;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Service.GroupService;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@ActiveProfiles("test")
@Sql(value = "/data.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GroupControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupService groupService;



    @Test
    @DisplayName("그룹 생성 테스트")
    @WithUserDetails(value = "7", userDetailsServiceBeanName = "customUserDetailsService")
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


    @Test
    @DisplayName("그룹원 조회 테스트")
    @WithUserDetails(value = "3", userDetailsServiceBeanName = "customUserDetailsService")
    public void find_all_group_member_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc.perform(
                get("/group")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        resultActions.andExpect(jsonPath("$.response.groupName").value("백소정 부산대점"))
                .andExpect(jsonPath("$.response.members[0].memberId").value(1))
                .andExpect(jsonPath("$.response.members[0].name").value("이재훈"))
                .andExpect(jsonPath("$.response.members[0].isAdmin").value(true))
                .andExpect(jsonPath("$.response.members[2].memberId").value(3))
                .andExpect(jsonPath("$.response.members[2].name").value("차지원"))
                .andExpect(jsonPath("$.response.members[2].isAdmin").value(false));

    }
}