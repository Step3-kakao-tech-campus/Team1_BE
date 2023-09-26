package com.example.team1_be.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @DisplayName("유저를 생성할 수 있어야 한다.")
    @Test
    void test1() {
        User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();
    }

    @DisplayName("유저를 저장할 수 있어야 한다.")
    @Test
    void test2() {
        User user = User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();

        userRepository.save(user);
    }
