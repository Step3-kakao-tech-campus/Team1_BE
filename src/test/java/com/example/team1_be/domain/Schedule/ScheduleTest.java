package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    @DisplayName("build 패턴적용")
    @Test
    void test1() {
        Group group = Group.builder()
                .id(1)
                .name("이재훈")
                .build();
        Schedule schedule = Schedule.builder()
                .id(1)
                .group(group)
                .build();
        assertThat(schedule.getGroup())
                .isEqualTo(group);
        assertThat(schedule.getGroup().getName())
                .isEqualTo("이재훈");
    }
}