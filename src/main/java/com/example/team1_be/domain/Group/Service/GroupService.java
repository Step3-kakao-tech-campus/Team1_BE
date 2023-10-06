package com.example.team1_be.domain.Group.Service;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupCreateRequest;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Member.exception.MemberExistsException;
import com.example.team1_be.domain.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final InviteRepository inviteRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;


    public void generateInviteCode() {
        String code = UUID.randomUUID().toString();

    }

    public void create(User user, GroupCreateRequest groupCreateRequest) {
        // 멤버 조회
        Optional<Member> optionalMember = memberRepository.findByUser(user);

        // 이미 다른 그룹의 멤버라면 예외 발생
        if (optionalMember.isPresent()) {
            throw new MemberExistsException();
        }

        // 그룹 생성
        Group group = Group.builder()
                .name(groupCreateRequest.getMarketName())
                .phoneNumber(groupCreateRequest.getPhoneNumber())
                .businessNumber(groupCreateRequest.getMarketNumber())
                .address(groupCreateRequest.getMainAddress() + groupCreateRequest.getDetailAddress())
                .build();

        // 멤버 객체 생성
        Member member = Member.builder()
                .isAdmin(true)
                .group(group)
                .user(user)
                .build();

        groupRepository.save(group);
        memberRepository.save(member);
    }
}