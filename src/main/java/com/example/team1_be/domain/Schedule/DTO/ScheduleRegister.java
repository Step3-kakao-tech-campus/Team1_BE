package com.example.team1_be.domain.Schedule.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 스케줄 등록 Request DTO
 */

public class ScheduleRegister {
    @Getter
    @NoArgsConstructor
    private static class Request {
        //요일별 스케줄 등록
        private Schedule monday;
        private Schedule tuesday;
        private Schedule wednesday;
        private Schedule thursday;
        private Schedule friday;
        private Schedule saturday;
        private Schedule sunday;

        public class Schedule {
            private String title;               // 시간대명
            private String startTime;           // 시작 시간
            private String endTime;             // 종료 시간
            private Integer amount;             // 인원수
        }
    }

}
