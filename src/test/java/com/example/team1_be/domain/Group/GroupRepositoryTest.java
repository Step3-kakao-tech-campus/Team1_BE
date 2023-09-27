package com.example.team1_be.domain.Group;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private EntityManager em;

    @AfterEach
    public void resetRepository() {
        em.clear();
        groupRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Group_tb ALTER COLUMN `group_id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
    }

    @DisplayName("그룹을 생성할 수 있다.")
    @Test
    void test1() {
        Group.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .address("부산광역시")
                .build();
    }

    @DisplayName("그룹을 저장할 수 있다.")
    @Test
    void test2() {
        Group group = Group.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .address("부산광역시")
                .build();
        groupRepository.save(group);
    }

    @DisplayName("그룹을 조회할 수 있다.")
    @Test
    void test3() {
        Group group = Group.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .address("부산광역시")
                .build();
        groupRepository.save(group);
        Group group1 = groupRepository.findById(1).orElse(null);
        assertThat(group1).isNotEqualTo(null);
    }
}