package com.example.team1_be.domain.Substitute;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubstitueRepositoryTest {
    @DisplayName("대타 신청서를 생성할 수 있다.")
    @Test
    void test1() {
        Substitute.builder()
                .content("이런 사유로 대타 신청합니다.")
                .adminApprove(false)
                .build();
    }
}