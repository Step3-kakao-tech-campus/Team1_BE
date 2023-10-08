package com.example.team1_be.domain.Schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 스케줄 신청/수정 제출 Request DTO
 */
@Getter
@NoArgsConstructor
public class ScheduleSubmissionRequest {
    private List<String> monday;
    private List<String> tuesday;
    private List<String> wednesday;
    private List<String> thursday;
    private List<String> friday;
    private List<String> saturday;
    private List<String> sunday;
}
