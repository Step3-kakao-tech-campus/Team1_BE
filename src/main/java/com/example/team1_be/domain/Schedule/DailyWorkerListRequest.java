package com.example.team1_be.domain.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일간 근무 명단 조회 Request DTO
 */
@Getter
@NoArgsConstructor
public class DailyWorkerListRequest {
    private Integer date;
    private Integer month;
    private Integer year;
}
