package com.example.team1_be.domain.Schedule.DTO;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RecruitSchedule {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull(message = "스케줄의 시작날짜를 입력하세요.")
        private LocalDate weekStartDate;
        @Valid
        @NotEmpty(message = "일주일의 모든 요일에 대한 정보가 있어야 합니다.")
        private List<DailySchedule> weeklyAmount;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class DailySchedule {
            @Valid
            private List<WokrtimeSchedule> dailySchedules;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class WokrtimeSchedule {
            @NotBlank(message = "근무 이름을 기입해야 합니다.")
            private String title;
            @NotNull(message = "근무 시작 시간을 기입해야 합니다.")
            private LocalTime startTime;
            @NotNull(message = "근무 종료 시간을 기입해야 합니다.")
            private LocalTime endTime;
            @NotNull(message = "근무 투입인원을 기입해야 합니다.")
            private int amount;
        }
    }
}
