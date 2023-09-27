package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ScheduleRepositoryTest {
    @DisplayName("스케줄을 생성할 수 있다.")
    @Test
    void test1() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .phoneNumber("011-1111-1111")
                .address("부산광역시")
                .build();

        Schedule.builder()
                .id(1)
                .group(group)
                .build();
    }
}