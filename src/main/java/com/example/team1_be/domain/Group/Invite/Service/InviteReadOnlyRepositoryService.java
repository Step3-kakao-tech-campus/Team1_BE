package com.example.team1_be.domain.Group.Invite.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InviteReadOnlyRepositoryService {
	private final InviteRepository repository;

	private final int INVITATION_EXPIRED_HOURS = 24;

	public Invite findByCode(String invitationKey) {
		return repository.findByCode(invitationKey)
			.orElseThrow(() -> new CustomException(ClientErrorCode.GROUP_NOT_FOUND, HttpStatus.BAD_REQUEST));	// 존재하지 않는 그룹입니다.
	}

	public boolean isDuplicateCode(String uuid) {
		try {
			findByCode(uuid);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String generateInviteCode() {
		String code;
		do {
			code = UUID.randomUUID().toString();
		} while (isDuplicateCode(code));
		return code;
	}

	public void checkValidation(Invite invite) {
		if (invite.getRenewedAt() == null) {
			throw new CustomException("유효하지 않은 요청입니다.", HttpStatus.FORBIDDEN);
		}
		if (invite.getRenewedAt().plusHours(INVITATION_EXPIRED_HOURS).isBefore(LocalDateTime.now())) {
			throw new CustomException("만료된 코드입니다. 재발급 받으세요", HttpStatus.BAD_REQUEST);
		}
	}

	public Invite findByGroup(Group group) {
		return repository.findByGroup(group)
			.orElseThrow(() -> new RuntimeException("그룹원과 초대장이 1:1이 되지 않는 에러입니다."));
	}
}
