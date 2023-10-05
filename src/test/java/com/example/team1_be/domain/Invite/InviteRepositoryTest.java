package com.example.team1_be.domain.Invite;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.UUID;

@DataJpaTest
class InviteRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private EntityManager em;

    @AfterEach
    public void resetRepository() {
        em.clear();
        groupRepository.deleteAll();
        inviteRepository.deleteAll();
//        em.createNativeQuery("")
//                .executeUpdate();
        em.clear();
    }

    @DisplayName("코드 생성 테스트")
    @Test
    void test1(){
        String code = UUID.randomUUID().toString();
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("051-111-2222")
                .address("부산광역시")
                .build();

        Invite invite = Invite.builder()
                .id(1L)
                .code(code)
                .group(group)
                .build();

        invite.getCode().equals(code);
    }


}
