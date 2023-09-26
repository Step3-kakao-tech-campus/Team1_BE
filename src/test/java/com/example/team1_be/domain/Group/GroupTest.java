package com.example.team1_be.domain.Group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    @DisplayName("그룹에는 이름, 매장전화번호, 주소의 정보를 저장할 수 있다.")
    @Test
    void test1() {
        Group group = new Group(1, "이재훈","051-3233-3333","부산광역시 ");
    }
}