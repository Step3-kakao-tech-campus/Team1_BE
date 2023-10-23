package com.example.team1_be.domain.Schedule.DTO;

import com.example.team1_be.domain.Worktime.Worktime;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LoadLatestSchedule {
    @Getter
    public static class Response {
        private List<Template> template;

        public Response(List<Worktime> lastestWorktimes) {
            template = new ArrayList<>();
            for (Worktime worktime:lastestWorktimes) {
                template.add(new Template(worktime));
            }
        }

        @Getter
        private class Template {
            private String title;
            private LocalTime startTime;
            private LocalTime endTime;

            public Template(Worktime worktime) {
                this.title = worktime.getTitle();
                this.startTime = worktime.getStartTime();
                this.endTime = worktime.getEndTime();
            }
        }
    }
}
