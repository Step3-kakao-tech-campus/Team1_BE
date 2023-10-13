package com.example.team1_be.domain.Group.Invite;

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
}
