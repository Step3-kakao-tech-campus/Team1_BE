package com.example.team1_be.domain.Schedule.DTO;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Worktime.Worktime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class WeeklyScheduleCheck {
    @Getter
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String weekStartDate;
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private List<List<ApplyStatus>> applyStatus;

        public Response(List<List<Worktime>> weeklyWorktime, List<List<List<Apply>>> applyList) {
            this.applyStatus = new ArrayList<>();
            IntStream.range(0, weeklyWorktime.size()).forEach(
                    (weeklyIdx) -> {
                        List<Worktime> dailyWorktime = weeklyWorktime.get(weeklyIdx);
                        List<List<Apply>> dailyApply = applyList.get(weeklyIdx);
                        List<ApplyStatus> dailyApplyStatusList= new ArrayList<>();
                        IntStream.range(0, dailyWorktime.size()).forEach(
                                (dailyIndex) -> {
                                    dailyApplyStatusList.add(new ApplyStatus(dailyWorktime.get(dailyIndex), dailyApply.get(dailyIndex)));
                                }
                        );
                        applyStatus.add(dailyApplyStatusList);
                    }
            );
        }

        @Getter
        public static class ApplyStatus {
            private String title;
            private LocalTime startTime;
            private LocalTime endTime;
            private List<Worker> workerList;

            public ApplyStatus(Worktime worktimeList, List<Apply> applyList) {
                this.title = worktimeList.getTitle();
                this.startTime = worktimeList.getStartTime();
                this.endTime = worktimeList.getEndTime();
                this.workerList = applyList.stream()
                        .map(apply -> apply.getMember())
                        .map(member -> new Worker(member))
                        .collect(toList());
            }
        }

        @Getter
        public static class Worker {
            private Long memberId;
            private String name;

            public Worker(Member member) {
                this.memberId = member.getId();
                this.name = member.getUser().getName();
            }
        }
    }
}
