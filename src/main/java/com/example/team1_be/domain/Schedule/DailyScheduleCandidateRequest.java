package com.example.team1_be.domain.Schedule;

import lombok.Data;

/**
 * 특정일 근무 가능 pool 확인 Request DTO
 */
@Data
public class DailyScheduleCandidateRequest {
    Integer date;
    Integer month;
    Integer year;
}
