package com.example.team1_be.domain.Schedule.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ScheduleApplicationStatus {
    @Getter
    @NoArgsConstructor
    private static class Response {
        private List<String> monday;
        private List<String> tuesday;
        private List<String> wednesday;
        private List<String> thursday;
        private List<String> friday;
        private List<String> saturday;
        private List<String> sunday;
    }
}
