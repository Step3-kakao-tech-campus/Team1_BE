package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
class ScheduleControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;

    @WithMockCustomUser
    @DisplayName("스케줄 모집 성공")
    @Test
    void test1() throws Exception {
        List<RecruitSchedule.Request.WorktimeSchedule> mondayWorks = new ArrayList<>();
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .title("오픈")
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .title("미들")
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .title("마감")
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());
        RecruitSchedule.Request.DailySchedule monday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(mondayWorks).build();
        RecruitSchedule.Request.DailySchedule tuesday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule wednesday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule thursday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule friday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule saturday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule sunday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();

        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weekStartDate(LocalDate.parse("2022-10-16"))
                .weeklyAmount(List.of(new RecruitSchedule.Request.DailySchedule[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday}))
                .build();
        String request = om.writeValueAsString(requestDTO);
        System.out.println(request);

        ResultActions perform = mvc.perform(post("/schedule/worktime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform.andExpect(status().isOk());
    }

    @WithMockCustomUser
    @DisplayName("스케줄 모집 실패(startDate 미기입)")
    @Test
    void test2() throws Exception {
        List<RecruitSchedule.Request.WorktimeSchedule> mondayWorks = new ArrayList<>();
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .title("오픈")
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .title("미들")
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .title("마감")
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());
        RecruitSchedule.Request.DailySchedule monday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(mondayWorks).build();
        RecruitSchedule.Request.DailySchedule tuesday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule wednesday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule thursday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule friday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule saturday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();
        RecruitSchedule.Request.DailySchedule sunday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(new ArrayList<>()).build();

        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weeklyAmount(List.of(new RecruitSchedule.Request.DailySchedule[]{monday, tuesday, wednesday, thursday, friday, saturday, sunday}))
                .build();
        String request = om.writeValueAsString(requestDTO);
        System.out.println(request);

        ResultActions perform = mvc.perform(post("/schedule/worktime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform.andExpect(status().isBadRequest());
    }

    @WithMockCustomUser
    @DisplayName("스케줄 모집 실패(weeklyAmount 미기입)")
    @Test
    void test3() throws Exception {
        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weekStartDate(LocalDate.parse("2022-10-06"))
                .build();
        String request = om.writeValueAsString(requestDTO);
        System.out.println(request);

        ResultActions perform = mvc.perform(post("/schedule/worktime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform.andExpect(status().isBadRequest());
    }

    @WithMockCustomUser
    @DisplayName("스케줄 모집 실패(weeklyAmount 빈 스케줄)")
    @Test
    void test4() throws Exception {
        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weekStartDate(LocalDate.parse("2022-10-06"))
                .weeklyAmount(new ArrayList<>())
                .build();
        String request = om.writeValueAsString(requestDTO);
        System.out.println(request);

        ResultActions perform = mvc.perform(post("/schedule/worktime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform.andExpect(status().isBadRequest());
    }

    @WithMockCustomUser
    @DisplayName("스케줄 모집 성공(weeklyAmount 공백 데이터만 추가)")
    @Test
    void test5() throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ArrayList<>());

        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weekStartDate(LocalDate.parse("2022-10-06"))
                .weeklyAmount(arrayList)
                .build();
        String request = om.writeValueAsString(requestDTO);
        System.out.println(request);

        ResultActions perform = mvc.perform(post("/schedule/worktime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform.andExpect(status().isBadRequest());
    }

    @WithMockCustomUser
    @DisplayName("스케줄 모집 실패(WorktimeSchedule 미기입")
    @Test
    void test6() throws Exception {
        List<RecruitSchedule.Request.WorktimeSchedule> mondayWorks = new ArrayList<>();
        mondayWorks.add(RecruitSchedule.Request.WorktimeSchedule.builder()
                .startTime(LocalTime.parse("10:00:00"))
                .endTime(LocalTime.parse("12:00:00"))
                .amount(10)
                .build());

        RecruitSchedule.Request.DailySchedule monday = RecruitSchedule.Request.DailySchedule.builder().dailySchedules(mondayWorks).build();

        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weekStartDate(LocalDate.parse("2022-10-06"))
                .weeklyAmount(List.of(new RecruitSchedule.Request.DailySchedule[]{monday}))
                .build();
        String request = om.writeValueAsString(requestDTO);
        System.out.println(request);

        ResultActions perform = mvc.perform(post("/schedule/worktime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform.andExpect(status().isBadRequest());
    }
}