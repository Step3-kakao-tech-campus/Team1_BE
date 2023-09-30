package com.example.team1_be.domain.Schedule;

import lombok.Data;

import java.util.List;

/**
 * 스케줄 신청/수정 현황 조회 Response DTO
 */
@Data
public class ScheduleApplicationStatusResponse {
    // 요일별 시간대 신청 현황
    List<String> monday;
    List<String> tuesday;
    List<String> wednesday;
    List<String> thursday;
    List<String> friday;
    List<String> saturday;
    List<String> sunday;
}
