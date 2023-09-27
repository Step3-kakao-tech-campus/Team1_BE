package com.example.team1_be.domain.Week;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Schedule.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WeekRepositoryTest {
    @DisplayName("한주의 정보를 생성할 수 있다.")
    @Test
    void test1() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .phoneNumber("010-1111-1111")
                .address("부산광역시")
                .build();

        Schedule schedule = Schedule.builder()
                .id(1)
                .group(group)
                .build();

        Week.builder()
                .id(1)
                .schedule(schedule)
                .startTime(LocalDateTime.now());
    }
}