package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Schedule.DTO.FixSchedule;
import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
import com.example.team1_be.domain.Schedule.Recommend.RecommendedWeeklySchedule;
import com.example.team1_be.domain.Schedule.Recommend.RecommendedWeeklyScheduleRepository;
import com.example.team1_be.domain.Schedule.Recommend.RecommendedWorktimeApply;
import com.example.team1_be.domain.Schedule.Recommend.RecommendedWorktimeApplyRepository;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/data.sql")
class ScheduleControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private RecommendedWorktimeApplyRepository recommendedWorktimeApplyRepository;
    @Autowired
    private RecommendedWeeklyScheduleRepository recommendedWeeklyScheduleRepository;

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
    @DisplayName("스케줄 모집 실패(중첩 빈배열)")
    @Test
    void test5() throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ArrayList<>());

        RecruitSchedule.Request requestDTO = RecruitSchedule.Request.builder()
                .weekStartDate(LocalDate.parse("2022-10-06"))
                .weeklyAmount(arrayList)
                .build();
        String request = om.writeValueAsString(requestDTO);

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

    @DisplayName("주별 스케줄 신청 현황 조회 실패(매니저-모집중아님)")
    @WithMockCustomUser(username = "eunjin", isAdmin = "true")
    @Test
    void weeklyScheduleCheck1() throws Exception {
        LocalDate startWeekDate = LocalDate.parse("2023-10-09");
        ResultActions perform = mvc.perform(get(String.format("/schedule/remain/week/%s", startWeekDate)));

        perform.andExpect(status().isNotFound());
        perform.andDo(print());
    }

    @DisplayName("주별 스케줄 신청 현황 조회 실패(잘못된 날짜 양식)")
    @WithMockCustomUser
    @Test
    void weeklyScheduleCheck2() throws Exception {
        String wrongDate = "10-10";
        ResultActions perform = mvc.perform(get(String.format("/schedule/remain/week/%s", wrongDate)));

        perform.andExpect(status().isBadRequest());
        perform.andDo(print());
    }

    @DisplayName("주별 스케줄 신청 현황 조회 실패(매니저 시작한 주 신청아님)")
    @WithMockCustomUser(username = "eunjin", isAdmin = "true")
    @Test
    void weeklyScheduleCheck3() throws Exception {
        LocalDate startWeekDate = LocalDate.parse("2023-10-09");
        ResultActions perform = mvc.perform(get(String.format("/schedule/remain/week/%s", startWeekDate)));

        perform.andExpect(status().isNotFound());
    }

    @DisplayName("주별 스케줄 신청 현황 조회 실패(알바생 마감한 주 신청아님)")
    @WithMockCustomUser(userId = "2", kakaoId = "2")
    @Test
    void weeklyScheduleCheck4() throws Exception {
        LocalDate startWeekDate = LocalDate.parse("2023-10-16");
        ResultActions perform = mvc.perform(get(String.format("/schedule/remain/week/%s", startWeekDate)));

        perform.andExpect(status().isNotFound());
    }

    @DisplayName("주별 스케줄 신청 현황 조회 성공(매니저)")
    @WithMockCustomUser(username = "eunjin", isAdmin = "true")
    @Test
    void weeklyScheduleCheck5() throws Exception {
        LocalDate startWeekDate = LocalDate.parse("2023-10-16");
        ResultActions perform = mvc.perform(get(String.format("/schedule/remain/week/%s", startWeekDate)));

        perform.andExpect(status().isOk());
        perform.andDo(print());
    }

    @DisplayName("주별 스케줄 신청 현황 조회 성공(알바생)")
    @WithMockCustomUser(userId = "2", kakaoId = "2")
    @Test
    void weeklyScheduleCheck7() throws Exception {
        LocalDate startWeekDate = LocalDate.parse("2023-10-09");
        ResultActions perform = mvc.perform(get(String.format("/schedule/remain/week/%s", startWeekDate)));

        perform.andExpect(status().isOk());
        perform.andDo(print());
    }

    @DisplayName("확정 스케줄 조회 성공")
    @WithMockCustomUser(userId = "2")
    @Test
    void getFixedWeeklySchedule1() throws Exception {
        YearMonth month = YearMonth.parse("2023-10");
        Long memberId = 2L;
        ResultActions perform = mvc.perform(
                get(String.format("/schedule/fix/month/%s/%s", month, memberId)));
        perform.andExpect(status().isOk());
        perform.andDo(print());
    }

    @DisplayName("확정 스케줄 조회 실패(파라미터 에러)")
    @WithMockCustomUser(userId = "2")
    @Test
    void getFixedWeeklySchedule2() throws Exception {
        Long memberId = 2L;
        ResultActions perform = mvc.perform(
                get(String.format("/schedule/fix/month/%s/%s", "2023", memberId)));
        perform.andExpect(status().isBadRequest());
        perform.andDo(print());
    }

    @DisplayName("추천 스케줄 후보 리스팅")
    @WithMockCustomUser
    @Test
    void recommendSchedule1() throws Exception {
        LocalDate date = LocalDate.parse("2023-10-16");
        ResultActions perform = mvc.perform(
                get(String.format("/schedule/recommend/%s", date)));
        perform.andExpect(status().isOk());
        perform.andDo(print());
    }

    @DisplayName("스케줄 확정하기 성공")
    @WithMockCustomUser
    @Test
    void fixSchedule1() throws Exception {
        // given
        LocalDate date = LocalDate.parse("2023-10-16");
        mvc.perform(
                get(String.format("/schedule/recommend/%s", date)));

        // when
        FixSchedule.Request requestDTO = new FixSchedule.Request(1);
        String request = om.writeValueAsString(requestDTO);
        ResultActions perform = mvc.perform(
                post("/schedule/fix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        );
        perform.andExpect(status().isOk());
        assertThat(recommendedWeeklyScheduleRepository.findAll().size()).isEqualTo(0);
        assertThat(recommendedWorktimeApplyRepository.findAll().size()).isEqualTo(0);
        perform.andDo(print());
    }
}