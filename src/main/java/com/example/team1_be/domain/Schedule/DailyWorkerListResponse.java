package com.example.team1_be.domain.Schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 일간 근무 명단 조회 Response DTO
 */
@Data
public class DailyWorkerListResponse {
    @Getter
    @Setter
    private class DailySchedule {
        String title;                   // 시간대명
        List<WorkerList> workerList;    // 일간 근무자 명단
    }

    @Getter
    @Setter
    private class WorkerList {
        Integer memberId;               // 근무자 id
        String name;                    // 근무자 이름
    }

    @JsonProperty("schedule")
    List<DailySchedule> dailySchedules;
}
