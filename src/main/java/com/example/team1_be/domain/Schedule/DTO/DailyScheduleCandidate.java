package com.example.team1_be.domain.Schedule.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DailyScheduleCandidate {
    @Getter
    @NoArgsConstructor
    private static class Request {
        private Integer date;
        private Integer month;
        private Integer year;
    }

    @Getter
    @NoArgsConstructor
    private static class Response {
        private String title;
        private List<Applicant> applicants;

        private Integer amount;
        private class Applicant {
            private Integer memberId;
            private String name;
        }

    }
}
