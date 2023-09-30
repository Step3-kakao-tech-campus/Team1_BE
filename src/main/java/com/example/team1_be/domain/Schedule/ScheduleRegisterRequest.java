package com.example.team1_be.domain.Schedule;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * 스케줄 등록 Request DTO
 */
@Data
public class ScheduleRegisterRequest {

    @Getter
    @Setter
    private class Schedule {
        String title;               // 시간대명
        LocalTime startTime;        // 시작 시간
        LocalTime endTime;          // 종료 시간
        Integer amount;             // 인원수
    }

    //요일별 스케줄 등록
    Schedule monday;
    Schedule tuesday;
    Schedule wednesday;
    Schedule thursday;
    Schedule friday;
    Schedule saturday;
    Schedule sunday;
}
