package com.example.team1_be.domain.Schedule;

import lombok.Data;

import java.util.List;

/**
 * 스케줄 신청/수정 제출 Request DTO
 */
@Data
public class ScheduleSubmissionRequest {
    List<String> monday;
    List<String> tuesday;
    List<String> wednesday;
    List<String> thursday;
    List<String> friday;
    List<String> saturday;
    List<String> sunday;
}
