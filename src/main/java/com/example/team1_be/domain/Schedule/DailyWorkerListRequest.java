package com.example.team1_be.domain.Schedule;

import lombok.Data;

/**
 * 일간 근무 명단 조회 Request DTO
 */
@Data
public class DailyWorkerListRequest {
    Integer date;
    Integer month;
    Integer year;
}
