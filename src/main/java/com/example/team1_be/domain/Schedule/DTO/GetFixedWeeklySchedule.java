package com.example.team1_be.domain.Schedule.DTO;

import com.example.team1_be.domain.Worktime.Worktime;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetFixedWeeklySchedule {
    @Getter
    public static class Response {
        private List<DailySchedule> schedule;
        private WorkSummary work_summary;

        public Response(List<Worktime> memberWorktimes, double monthly, double v) {
            this.work_summary = new WorkSummary(v, monthly);

            List<LocalDate> worktimeDates = new ArrayList<>(memberWorktimes.stream()
                    .map(worktime ->
                            worktime.getDay().getWeek().getStartDate()
                                    .plusDays(worktime.getDay().getDayOfWeek()))
                    .collect(Collectors.toSet()));
            Collections.sort(worktimeDates);

            this.schedule = new ArrayList<>();
            for (LocalDate workDate : worktimeDates) {
                DailySchedule dailySchedule = new DailySchedule(workDate, memberWorktimes.stream()
                        .filter(x -> x.getDay().getWeek().getStartDate().plusDays(x.getDay().getDayOfWeek()).equals(workDate))
                        .map(y -> y.getTitle())
                        .collect(Collectors.toList()));
                this.schedule.add(dailySchedule);
            }

        }

        @Getter
        private class WorkSummary {
            private Double weekly;
            private Double monthly;

            public WorkSummary(Double weekly, Double monthly) {
                this.weekly = weekly;
                this.monthly = monthly;
            }
        }

        @Getter
        private class DailySchedule {
            private LocalDate date;
            private List<String> workTime;

            public DailySchedule(LocalDate date, List<String> workTime) {
                this.date = date;
                this.workTime = workTime;
            }
        }
    }
}
