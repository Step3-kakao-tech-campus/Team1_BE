package com.example.team1_be.domain.Schedule.DTO;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GetDailyFixedApplies {
    @Getter
    public static class Response {
        private List<DailyWorktimeApply> schedule;

        public Response(List<Worktime> worktimes, List<List<Apply>> dailyAplies) {
            this.schedule = new ArrayList<>();
            IntStream.range(0, worktimes.size())
                    .forEach(i -> schedule.add(new DailyWorktimeApply(worktimes.get(i), dailyAplies.get(i))));
        }

        @Getter
        private class DailyWorktimeApply {
            private String title;
            private LocalTime startTime;
            private LocalTime endTime;
            private List<Worker> workerList;

            public DailyWorktimeApply(Worktime worktime, List<Apply> applies) {
                this.title = worktime.getTitle();
                this.startTime = worktime.getStartTime();
                this.endTime = worktime.getEndTime();
                this.workerList = new ArrayList<>();
                applies.forEach(apply -> workerList.add(new Worker(apply)));
            }

            @Getter
            private class Worker {
                private Long userId;
                private String name;

                public Worker(Apply apply) {
                    this.userId = apply.getUser().getId();
                    this.name = apply.getUser().getName();
                }
            }
        }
    }
}
