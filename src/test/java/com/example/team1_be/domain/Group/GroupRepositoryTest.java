package com.example.team1_be.domain.Group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GroupRepositoryTest {


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
}