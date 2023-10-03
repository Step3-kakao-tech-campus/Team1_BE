package com.example.team1_be.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 스케줄 신청/수정 현황 조회 Response DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleApplicationStatusResponse {
    // 요일별 시간대 신청 현황
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;
    private List<String> sunday;
}
