package com.example.team1_be.domain.Member;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @AfterEach
    public void resetRepository() {
        em.clear();

        memberRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Member_tb ALTER COLUMN `member_id` RESTART WITH 1")
                .executeUpdate();

        userRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE User_tb ALTER COLUMN `user_id` RESTART WITH 1")
                .executeUpdate();

        groupRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE Group_tb ALTER COLUMN `group_id` RESTART WITH 1")
                .executeUpdate();

        em.clear();
    }

    @DisplayName("그룹원을 생성할 수 있다.")
    @Test
    void test1() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .phoneNumber("011-1111-1111")
                .address("부산광역시")
                .build();

        User user = User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-2222-2222")
                .build();

        Member.builder()
                .isAdmin(false)
                .user(user)
                .group(group)
                .build();
    }

    @DisplayName("그룹원을 저장할 수 있다.")
    @Test
    void test2() {
        Group group = Group.builder()
                .id(1)
                .name("맘스터치")
                .phoneNumber("011-1111-1111")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        User user = User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-2222-2222")
                .build();
        userRepository.save(user);

        Member member = Member.builder()
                .isAdmin(false)
                .user(user)
                .group(group)
                .build();
        memberRepository.save(member);
    }
}