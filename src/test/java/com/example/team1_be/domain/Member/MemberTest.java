package com.example.team1_be.domain.Member;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @DisplayName("멤버는 그룹과 유저의 정보를 가진다.")
    @Test
    void test1() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .build();
        User user = User.builder()
                .phoneNumber("010-5538-6818")
                .name("이재훈")
                .build();

        Boolean memberState = true;
        Member member = Member.builder()
                .id(1)
                .group(group)
                .user(user)
                .isAdmin(memberState)
                .build();

        assertThat(member.isAdmin()).isEqualTo(memberState);
    }
}