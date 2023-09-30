package com.example.team1_be.domain.Schedule;

import lombok.Data;

/**
 * 스케줄 확정 Request DTO
 */
@Data
public class ScheduleApproveRequest {
    Integer selection;  //선택한 추천 스케줄 id
}
