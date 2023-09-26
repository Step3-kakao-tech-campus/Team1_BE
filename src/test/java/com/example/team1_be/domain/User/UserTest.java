package com.example.team1_be.domain.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @DisplayName("유저의 정보(이름, 전화번호)를 조회할 수 있다.")
    @CsvSource({"1, 이재훈, 010-1234-4466",
                "2, 안한주, 010-3333-3333"})
    @ParameterizedTest
    void test1(int id, String name, String phoneNumber) {
        User user = new User(id, name, phoneNumber);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @DisplayName("유저의 이름길이는 2글자 미만이 될 수 없다.")
    @Test
    void test2() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new User(1, "이", "010-5538-6818"));
    }
    }
}