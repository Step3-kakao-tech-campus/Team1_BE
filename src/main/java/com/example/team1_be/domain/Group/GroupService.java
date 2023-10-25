package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteService;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberService;
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
    private final MemberService memberService;

    private final GroupRepository groupRepository;

    @Transactional
    public void create(User user, Create.Request request) {
        if (!user.getIsAdmin()) {
            throw new CustomException("매니저 계정만 그룹을 생성할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        memberService.findByUser(user);

        Group group = Group.builder()
                .name(request.getMarketName())
                .address(request.getMainAddress() + request.getDetailAddress())
                .businessNumber(request.getMarketNumber())
                .build();
        groupRepository.save(group);

        Invite invite = Invite.builder()
                .code(inviteService.generateInviteCode())
                .group(group)
                .build();
        inviteService.createInvite(invite);

        Member member = Member.builder()
                .user(user)
                .group(group)
                .build();
        memberService.createMember(member);
    }

    @Transactional
    public void invitationAccept(User user, InvitationAccept.Request request) {
        if (user.getIsAdmin()) {
            throw new CustomException("알바생 계정만 그룹에 가입할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        Invite invite = inviteService.findByCode(request.getInvitationKey());
        inviteService.checkValidation(invite);

        Group group = invite.getGroup();
        Member member = Member.builder()
                .group(group)
                .user(user)
                .build();
        memberService.createMember(member);
    }

    public GetMembers.Response getMembers(User user) {
        Member member = memberService.findByUser(user);

        Group group = member.getGroup();
        List<Member> members = memberService.findAllByGroup(group);

        return new GetMembers.Response(group, user, members);
    }

    public Group findByUser(User user) {
        return groupRepository.findByUser(user.getId())
                .orElseThrow(() -> new CustomException("그룹에 가입되어있지 않습니다.", HttpStatus.FORBIDDEN));
    }
}
