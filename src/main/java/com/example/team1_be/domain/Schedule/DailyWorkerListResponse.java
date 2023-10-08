package com.example.team1_be.domain.Schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 일간 근무 명단 조회 Response DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkerListResponse {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailySchedule {
        private String title;                   // 시간대명
        private List<WorkerList> workerList;    // 일간 근무자 명단
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkerList {
        private Integer memberId;               // 근무자 id
        private String name;                    // 근무자 이름
    }

    @JsonProperty("schedule")
    private List<DailySchedule> dailySchedules;
}
