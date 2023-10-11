package com.example.team1_be.domain.Schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 근무자 개별 확정 스케줄 확인 Response DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IndividualScheduleConfirmedResponse {

    // 일별 스케줄 목록
    @Getter
    public static class ScheduleConfirmed {
        private List<String> day1;
        private List<String> day2;
        private List<String> day3;
        private List<String> day4;
        private List<String> day5;
        private List<String> day6;
        private List<String> day7;
        private List<String> day8;
        private List<String> day9;
        private List<String> day10;
        private List<String> day11;
        private List<String> day12;
        private List<String> day13;
        private List<String> day14;
        private List<String> day15;
        private List<String> day16;
        private List<String> day17;
        private List<String> day18;
        private List<String> day19;
        private List<String> day20;
        private List<String> day21;
        private List<String> day22;
        private List<String> day23;
        private List<String> day24;
        private List<String> day25;
        private List<String> day26;
        private List<String> day27;
        private List<String> day28;
        private List<String> day29;
        private List<String> day30;
        private List<String> day31;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkSummary {
        private Double weekly;      // 주 근무 시간
        private Double monthly;     // 달 근무 시간
    }

    @JsonProperty("schedule")
    private ScheduleConfirmed scheduleConfirmed;    // 확정된 스케줄 목록

    @JsonProperty("work_summary")
    private WorkSummary workSummary;    // 근무 시간
}
