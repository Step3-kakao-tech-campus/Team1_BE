package com.example.team1_be.domain.Week;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WeekRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private EntityManager em;

    @AfterEach
    public void resetRepository() {
        em.clear();

        weekRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Week_tb ALTER COLUMN `week_id` RESTART WITH 1")
                .executeUpdate();

        scheduleRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Schedule_tb ALTER COLUMN `schedule_id` RESTART WITH 1")
                .executeUpdate();

        groupRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Group_tb ALTER COLUMN `group_id` RESTART WITH 1")
                .executeUpdate();

        em.clear();
    }

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
                .startTime(LocalDateTime.now())
                .build();
    }

    @DisplayName("한주의 정보를 저장할 수 있다.")
    @Test
    void test2() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .phoneNumber("010-1111-1111")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Schedule schedule = Schedule.builder()
                .id(1)
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        Week week = Week.builder()
                .id(1)
                .schedule(schedule)
                .startTime(LocalDateTime.now())
                .build();
        weekRepository.save(week);
    }
    
    @DisplayName("한주의 정보를 불러올 수 있다.")
    @Test
    void test3() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .phoneNumber("010-1111-1111")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Schedule schedule = Schedule.builder()
                .id(1)
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        Week week = Week.builder()
                .id(1)
                .schedule(schedule)
                .startTime(LocalDateTime.now())
                .build();
        weekRepository.save(week);

        assertThat(weekRepository.findById(1)
                .orElse(null))
                .isNotEqualTo(null);
    }
}