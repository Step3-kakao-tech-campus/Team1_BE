package com.example.team1_be.domain.Group.Invite;

import com.example.team1_be.domain.Group.DTO.GetInvitation;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Invite.DTO.InvitationCheck;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InviteService {
    private final InviteRepository inviteRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    private final int invitationExpiredHours = 24;

    public String generateInviteCode() {
        Invite invite;
        String code;
        do {
            code = UUID.randomUUID().toString();
            invite = inviteRepository.findByCode(code).orElse(null);
        } while (invite != null);
        return code;
    }

    public void checkValidation(Invite invite){
        if (invite.getRenewedAt() == null) {
            throw new CustomException("유효하지 않은 요청입니다.", HttpStatus.FORBIDDEN);
        }
        if (invite.getRenewedAt().plusHours(invitationExpiredHours).isBefore(LocalDateTime.now())){
            throw new CustomException("만료된 코드입니다. 재발급 받으세요", HttpStatus.BAD_REQUEST);
        }
    }

    public InvitationCheck.Response invitationCheck(String invitationKey) {
        Invite invite = inviteRepository.findByCode(invitationKey)
                .orElseThrow(() -> new CustomException("존재하지 않는 그룹입니다.", HttpStatus.NOT_FOUND));
        checkValidation(invite);
        return new InvitationCheck.Response(invite.getGroup());
    }

    public GetInvitation.Response getInvitation(User user) {
        Member member = memberRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("등록되지 않은 멤버입니다.", HttpStatus.BAD_REQUEST));
        if (!user.getIsAdmin()) {
            throw new CustomException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        Group group = member.getGroup();
        Invite invite = inviteRepository.findByGroup(group)
                .orElseThrow(() -> new RuntimeException("그룹원과 초대장이 1:1이 되지 않는 에러입니다."));
        inviteRepository.save(invite.renew());
        return new GetInvitation.Response(invite.getCode());
    }
}
