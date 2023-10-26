package com.example.team1_be.domain.Group.Invite;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.DTO.GetInvitation;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.Invite.DTO.InvitationCheck;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserService;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class InviteService {
	private final UserService userService;
	private final InviteRepository inviteRepository;

	private final int INVITATION_EXPIRED_HOURS = 24;

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

	public InvitationCheck.Response invitationCheck(String invitationKey) {
		Invite invite = findByCode(invitationKey);
		checkValidation(invite);
		return new InvitationCheck.Response(invite.getGroup());
	}

	@Transactional
	public GetInvitation.Response getInvitation(User user) {
		if (!user.getIsAdmin()) {
			throw new CustomException("매니저 계정만 초대장을 발급할 수 있습니다.", HttpStatus.FORBIDDEN);
		}
		Group group = userService.findGroupByUser(user);
		Invite invite = findByGroup(group);
		renewInvitation(invite);
		return new GetInvitation.Response(invite.getCode());
	}

	@Transactional
	public void createInvite(Invite invite) {
		inviteRepository.save(invite);
	}

	@Transactional
	public void renewInvitation(Invite invite) {
		invite.renew();
		createInvite(invite);
	}

	@Transactional
	public void createInviteWithGroup(Group group) {
		String invitationCode = generateInviteCode();
		createInvite(Invite.builder()
			.code(invitationCode)
			.group(group)
			.build());
	}

	public Invite findByCode(String invitationKey) {
		return inviteRepository.findByCode(invitationKey)
			.orElseThrow(() -> new CustomException("존재하지 않는 그룹입니다.", HttpStatus.NOT_FOUND));
	}

	public boolean isDuplicateCode(String uuid) {
		try {
			findByCode(uuid);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Invite findByGroup(Group group) {
		return inviteRepository.findByGroup(group)
			.orElseThrow(() -> new RuntimeException("그룹원과 초대장이 1:1이 되지 않는 에러입니다."));
	}
}
