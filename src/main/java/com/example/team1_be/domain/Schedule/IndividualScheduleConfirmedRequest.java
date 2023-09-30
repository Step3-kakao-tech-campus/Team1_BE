package com.example.team1_be.domain.Schedule;

import lombok.Data;

/**
 * 근무자 개별 확정 스케줄 확인 Request DTO
 */
@Data
public class IndividualScheduleConfirmedRequest {
    Integer memberId;       // member id
}
