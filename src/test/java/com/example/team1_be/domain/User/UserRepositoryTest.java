package com.example.team1_be.domain.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

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
        em.createNativeQuery("ALTER TABLE User_tb ALTER COLUMN `user_id` RESTART WITH 1")
                .executeUpdate();
        em.clear();
    }

    @DisplayName("유저를 생성할 수 있어야 한다.")
    @Test
    void test1() {
        User.builder()
                .id(1L)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();
    }

    @DisplayName("유저를 저장할 수 있어야 한다.")
    @Test
    void test2() {
        User user = User.builder()
                .id(1L)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();

        userRepository.save(user);
    }

    @DisplayName("유저를 불러올 수 있어야 한다.")
    @Test
    void test3() {
        User user = User.builder()
                .id(1L)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();

        userRepository.save(user);
        User newUser = userRepository.findById(1L).orElse(null);
        if (newUser!=null)
            assertThat(newUser.getName()).isEqualTo("이재훈");
    }

    @DisplayName("유저를 이름은 2글자 미만을 저장할 수 있어야 한다.")
    @Test
    void test4() {
        User user = User.builder()
                .id(1L)
                .name("이")
                .phoneNumber("010-5538-6818")
                .build();

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> userRepository.save(user));
    }

    @DisplayName("유저를 이름은 10글자를 넘길 수 없다.")
    @Test
    void test5() {
        User user = User.builder()
                .id(1L)
                .name("일이삼사오육칠팔구십일")
                .phoneNumber("010-5538-6818")
                .build();

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> userRepository.save(user));
    }

    @DisplayName("유저의 전화번호는 13자리여야 한다.")
    @Test
    void test6() {
        User user = User.builder()
                .id(1L)
                .name("이재훈")
                .phoneNumber("010-5538")
                .build();

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> userRepository.save(user));
    }
}