package com.example.team1_be.domain.Schedule.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
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

        private List<DaySchedule> schedule;

        @JsonProperty("work_summary")
        private WorkSummary workSummary;

        class DaySchedule {
            private Integer date;
            private List<String> workTime;
        }

        private class WorkSummary {
            private Double weekly;
            private Double monthly;
        }
    }
}
