package com.example.team1_be.domain.Invite;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InviteRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;

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
        //given
        String code = UUID.randomUUID().toString();
        //when
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("051-111-22222")
                .address("부산광역시")
                .build();
        groupRepository.save(group);
        Invite invite = Invite.builder()
                .id(1L)
                .code(code)
                .group(group)
                .build();
        inviteRepository.save(invite);
        //then
        invite.getCode().equals(code);
    }

    @DisplayName("링크 생성 테스트")
    @Test
    void test2(){
        //given
        String prefix = "/group/invitaion/";
        String code = UUID.randomUUID().toString();
        //when
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("051-111-22222")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Invite invite = Invite.builder()
                .id(1L)
                .code(code)
                .group(group)
                .build();
        inviteRepository.save(invite);

        String url = prefix+code;
        //then
        System.out.println(url);
    }
    @DisplayName("링크 접속 시 해당 그룹 멤버로 등록하는 테스트")
    @Test
    void test3(){
        //given
        String code = UUID.randomUUID().toString();
        //when
        Group group = Group.builder()
                .id(1L)
                .name("맘스터치")
                .phoneNumber("051-111-22222")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Invite invite = Invite.builder()
                .id(1L)
                .code(code)
                .group(group)
                .build();
        inviteRepository.save(invite);

        User user = User.builder()
                .id(1L)
                .kakaoId(1L)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();
        userRepository.save(user);

        //then
        if(code.equals(invite.getCode())){
            Member member = Member.builder()
                    .id(1L)
                    .isAdmin(false)
                    .group(group)
                    .user(user)
                    .build();
            memberRepository.save(member);
            assertThat(member.getId()).isEqualTo(1L);
        }else{
            System.out.println("틀린 url");
        }
    }

}
