package com.example.team1_be.domain.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 특정일 근무 가능 pool 확인 Request DTO
 */
@Getter
@NoArgsConstructor
public class DailyScheduleCandidateRequest {
    private Integer date;
    private Integer month;
    private Integer year;
}
