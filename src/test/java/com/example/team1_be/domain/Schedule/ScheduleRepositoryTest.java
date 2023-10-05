package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ScheduleRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private EntityManager em;

    @AfterEach
    public void resetRepository() {
        em.clear();

        scheduleRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Schedule_tb ALTER COLUMN `schedule_id` RESTART WITH 1")
                .executeUpdate();

        groupRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Group_tb ALTER COLUMN `group_id` RESTART WITH 1")
                .executeUpdate();

        em.clear();
    }

    @DisplayName("스케줄을 생성할 수 있다.")
    @Test
    void test1() {
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("011-1111-1111")
                .address("부산광역시")
                .build();

        Schedule.builder()
                .id(1L)
                .group(group)
                .build();
    }

    @DisplayName("스케줄을 저장할 수 있다.")
    @Test
    void test2() {
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("011-1111-1111")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Schedule schedule = Schedule.builder()
                .id(1L)
                .group(group)
                .build();
        scheduleRepository.save(schedule);
    }

    @DisplayName("스케줄을 불러올 수 있다.")
    @Test
    void test3() {
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("011-1111-1111")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Schedule schedule = Schedule.builder()
                .id(1L)
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        assertThat(scheduleRepository.findById(1)
                .orElse(null))
                .isNotEqualTo(null);
    }
}