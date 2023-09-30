package com.example.team1_be.domain.Schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 근무자 개별 확정 스케줄 확인 Response DTO
 */
@Data
public class IndividualScheduleConfirmedResponse {

    // 일별 스케줄 목록
    @Getter
    @Setter
    private class ScheduleConfirmed {
        List<String> day1;
        List<String> day2;
        List<String> day3;
        List<String> day4;
        List<String> day5;
        List<String> day6;
        List<String> day7;
        List<String> day8;
        List<String> day9;
        List<String> day10;
        List<String> day11;
        List<String> day12;
        List<String> day13;
        List<String> day14;
        List<String> day15;
        List<String> day16;
        List<String> day17;
        List<String> day18;
        List<String> day19;
        List<String> day20;
        List<String> day21;
        List<String> day22;
        List<String> day23;
        List<String> day24;
        List<String> day25;
        List<String> day26;
        List<String> day27;
        List<String> day28;
        List<String> day29;
        List<String> day30;
        List<String> day31;
    }

    @Getter
    @Setter
    private class WorkSummary {
        Double weekly;      // 주 근무 시간
        Double monthly;     // 달 근무 시간
    }

    @JsonProperty("schedule")
    ScheduleConfirmed scheduleConfirmed;    // 확정된 스케줄 목록

    @JsonProperty("work_summary")
    WorkSummary workSummary;    // 근무 시간
}
