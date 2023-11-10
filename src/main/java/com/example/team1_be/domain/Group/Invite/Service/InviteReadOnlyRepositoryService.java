package com.example.team1_be.domain.Group.Invite.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InviteReadOnlyRepositoryService {
	private final InviteRepository repository;

	private final int INVITATION_EXPIRED_HOURS = 24;

	public Invite findByCode(String invitationKey) {
		log.info("초대장 코드: {}에 대한 초대장을 조회합니다.", invitationKey);
		return repository.findByCode(invitationKey)
			.orElseThrow(() -> new CustomException("존재하지 않는 그룹입니다.", HttpStatus.NOT_FOUND));
	}

	public boolean isDuplicateCode(String uuid) {
		log.info("초대장 코드: {}가 중복되는지 확인합니다.", uuid);
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
		log.info("초대장의 유효성을 확인합니다.");
		if (invite.getRenewedAt() == null) {
			throw new CustomException("유효하지 않은 요청입니다.", HttpStatus.FORBIDDEN);
		}
		if (invite.getRenewedAt().plusHours(INVITATION_EXPIRED_HOURS).isBefore(LocalDateTime.now())) {
			throw new CustomException("만료된 코드입니다. 재발급 받으세요", HttpStatus.BAD_REQUEST);
		}
	}

	public Invite findByGroup(Group group) {
		log.info("그룹 ID: {}에 대한 초대장을 조회합니다.", group.getId());
		return repository.findByGroup(group)
			.orElseThrow(() -> new RuntimeException("그룹원과 초대장이 1:1이 되지 않는 에러입니다."));
	}
}
