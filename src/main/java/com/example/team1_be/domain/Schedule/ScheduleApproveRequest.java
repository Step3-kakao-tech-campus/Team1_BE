package com.example.team1_be.domain.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스케줄 확정 Request DTO
 */
@Getter
@NoArgsConstructor
public class ScheduleApproveRequest {
    private Integer selection;  //선택한 추천 스케줄 id
}
