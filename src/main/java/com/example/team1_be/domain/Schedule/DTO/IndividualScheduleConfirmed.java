package com.example.team1_be.domain.Schedule.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class IndividualScheduleConfirmed {
    @Getter
    @NoArgsConstructor
    private static class Request {
        private Integer memberId;
        private Integer month;
        private Integer year;
    }

    @Getter
    @NoArgsConstructor
    private static class Response {

        private ScheduleConfirmed schedule;
        private WorkSummary workSummary;

        private class ScheduleConfirmed {

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
        private class WorkSummary {
            private Double weekly;
            private Double monthly;
        }
    }
}
