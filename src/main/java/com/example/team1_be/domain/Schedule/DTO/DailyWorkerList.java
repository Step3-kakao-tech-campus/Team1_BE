package com.example.team1_be.domain.Schedule.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DailyWorkerList {
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
        private List<DailySchedule> schedules;

        private class DailySchedule {
            private String title;
            private List<WorkerList> workerList;

            private class WorkerList {
                private Integer memberId;
                private String name;
            }
        }
    }
}
