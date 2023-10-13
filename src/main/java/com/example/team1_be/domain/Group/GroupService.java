package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
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
                .isAdmin(true)
                .group(group)
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void invitationAccept(User user, InvitationAccept.Request request) {
        Invite invite = inviteRepository.findByCode(request.getInvitationKey())
                .orElseThrow(() -> new CustomException("존재하지 않는 그룹입니다", HttpStatus.NOT_FOUND));
        inviteService.checkValidation(invite);

        Group group = invite.getGroup();
        Member member = Member.builder()
                .group(group)
                .isAdmin(false)
                .user(user)
                .build();
        memberRepository.save(member);
    }
}
