package com.example.team1_be.domain.Group.Service;

import com.example.team1_be.domain.Group.*;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Member.exception.AuthorityException;
import com.example.team1_be.domain.Member.exception.MemberExistsException;
import com.example.team1_be.domain.Member.exception.MemberNotFoundException;
import com.example.team1_be.domain.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public GroupCreateResponse create(User user, GroupCreateRequest groupCreateRequest) {
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

        Group group1 = groupRepository.save(group);
        memberRepository.save(member);

        GroupCreateResponse groupCreateResponse = GroupCreateResponse.builder()
                .groupId(group1.getId())
                .groupName(group1.getName())
                .build();

        return groupCreateResponse;
    }


    public GroupMemberListResponse findAll(User user) {

        // 멤버 조회
        Member requesterMember = memberRepository.findByUser(user).orElseThrow(MemberNotFoundException::new);

        // 그룹 조회
        Group group = requesterMember.getGroup();

        // 그룹원 조회
        List<Member> members = memberRepository.findAllByGroup(group);

        // response DTO 생성
        List<GroupMemberListResponse.Member> memberList = new ArrayList<>();
        for (Member member : members) {
            GroupMemberListResponse.Member memberDto = GroupMemberListResponse.Member.builder()
                    .memberId(member.getId())
                    .name(member.getUser().getName())
                    .isAdmin(member.getIsAdmin())
                    .build();
            memberList.add(memberDto);
        }

        GroupMemberListResponse groupMemberListResponse = new GroupMemberListResponse(group.getName(), memberList);

        return groupMemberListResponse;
    }

    public void deleteMember(User user, Long memberId) {
        // 멤버 조회
        Member requester = memberRepository.findByUser(user).orElseThrow(() -> new MemberNotFoundException("멤버로 등록되지 않은 유저입니다."));

        // 권한 확인
        if (requester.getIsAdmin()) {
            throw new AuthorityException();
        }

        // 멤버 id로 퇴사자 멤버 객체 찾기
        Member quitter = memberRepository.findByUser(user).orElseThrow(() -> new MemberNotFoundException("해당 id로 멤버를 찾을 수 없습니다."));

        // 그룹이 다르다면 권한없음 예외
        if (!requester.getGroup().equals(quitter.getGroup())) {
            throw new AuthorityException();
        }

        // 해당 멤버 삭제
        memberRepository.delete(quitter);
    }
}