package com.example.team1_be.domain.Schedule.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스케줄 확정 Request DTO
 */
public class ScheduleApprove {
    @Getter
    @NoArgsConstructor
    private static class Request {
        private Integer selection;  //선택한 추천 스케줄 id
    }
}
