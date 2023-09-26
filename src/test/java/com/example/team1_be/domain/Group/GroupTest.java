package com.example.team1_be.domain.Group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    @DisplayName("그룹에는 이름, 매장전화번호, 주소의 정보를 저장할 수 있다.")
    @Test
    void test1() {
        Group group = new Group(1, "이재훈","051-3233-3333","부산광역시 ");
        Group group = new Group(1, "이재훈","051-3233-3333","부산광역시");
    }

    @DisplayName("그룹의 정보들을 조회할 수 있다.")
    @Test
    void test2() {
        Group group = new Group(1, "이재훈","051-3233-3333","부산광역시");
        assertThat(group.getId()).isEqualTo(1);
        assertThat(group.getName()).isEqualTo("이재훈");
        assertThat(group.getTelNumber()).isEqualTo("051-3233-3333");
        assertThat(group.getAddress()).isEqualTo("부산광역시");
    }
}