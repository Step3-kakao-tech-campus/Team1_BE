package com.example.team1_be.domain.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 근무자 개별 확정 스케줄 확인 Request DTO
 */
@Getter
@NoArgsConstructor
public class IndividualScheduleConfirmedRequest {
    private Integer memberId;       // 조회하는 근무자 id
    private Integer month;          // 조회하는 근무 달
    private Integer year;           // 조회하는 근무 년도
}
