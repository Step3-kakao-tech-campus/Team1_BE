package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import com.example.team1_be.domain.Group.Invite.InviteService;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {
    private final InviteService inviteService;

    private final GroupRepository groupRepository;
    private final InviteRepository inviteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(User user, Create.Request request) {
        if (user.getIsAdmin() == false) {
            throw new CustomException("매니저 계정만 그룹을 생성할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        if (memberRepository.findByUser(user).orElse(null) != null) {
            throw new CustomException("이미 그룹에 가입되어 있습니다.", HttpStatus.FORBIDDEN);
        }

        Group group = Group.builder()
                .name(request.getMarketName())
                .address(request.getMainAddress()+request.getDetailAddress())
                .businessNumber(request.getMarketNumber())
                .build();
        groupRepository.save(group);

        Invite invite = Invite.builder()
                .code(inviteService.generateInviteCode())
                .group(group)
                .build();
        inviteRepository.save(invite);

        Member member = Member.builder()
                .user(user)
                .group(group)
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void invitationAccept(User user, InvitationAccept.Request request) {
        if (user.getIsAdmin() == true) {
            throw new CustomException("알바생 계정만 그룹에 가입할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        Invite invite = inviteRepository.findByCode(request.getInvitationKey())
                .orElseThrow(() -> new CustomException("존재하지 않는 그룹입니다", HttpStatus.NOT_FOUND));
        inviteService.checkValidation(invite);

        Group group = invite.getGroup();
        Member member = Member.builder()
                .group(group)
                .user(user)
                .build();
        memberRepository.save(member);
    }

    public GetMembers.Response getMembers(User user) {
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST));

        Group group = member.getGroup();
        List<Member> members = memberRepository.findAllByGroup(group);

        return new GetMembers.Response(group, user, members);
    }
}
