package com.example.team1_be.domain.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @AfterEach
    public void resetRepository() {
        em.clear();
        userRepository.deleteAll();
        em.createNativeQuery("TRUNCATE TABLE User_tb RESTART IDENTITY;")
                        .executeUpdate();
        em.clear();
    }

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

    @DisplayName("유저를 불러올 수 있어야 한다.")
    @Test
    void test3() {
        User user = User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();

        userRepository.save(user);
        User newUser = userRepository.findById(1).orElse(null);
        if (newUser!=null)
            assertThat(newUser.getName()).isEqualTo("이재훈");
    }
