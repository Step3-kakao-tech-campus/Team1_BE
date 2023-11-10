package com.example.team1_be.domain.Group.Invite.Service;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.DTO.GetInvitation;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.Invite.DTO.InvitationCheck;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserService;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class InviteService {
	private final UserService userService;
	private final InviteReadOnlyRepositoryService readOnlyRepositoryService;
	private final InviteWriteRepositoryService writeRepositoryService;

	public InvitationCheck.Response invitationCheck(String invitationKey) {
		log.info("초대장 코드: {}에 대한 초대장을 검증합니다.", invitationKey);
		Invite invite = readOnlyRepositoryService.findByCode(invitationKey);
		readOnlyRepositoryService.checkValidation(invite);
		return new InvitationCheck.Response(invite.getGroup());
	}

	public GetInvitation.Response getInvitation(User user) {
		if (!user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MANAGER_API_REQUEST_ERROR, HttpStatus.FORBIDDEN);    // 매니저 계정만 초대장을 발급할 수 있습니다.
		}

		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}

		Invite invite = readOnlyRepositoryService.findByGroup(group);
		writeRepositoryService.renewInvitation(invite);
		return new GetInvitation.Response(invite.getCode());
	}

	public void createInviteWithGroup(Group group) {
		log.info("그룹 ID: {}에 대한 새로운 초대장을 생성합니다.", group.getId());
		String invitationCode = readOnlyRepositoryService.generateInviteCode();
		writeRepositoryService.createInvite(Invite.builder()
			.code(invitationCode)
			.group(group)
			.build());
	}

	public Invite findInvitation(String invitationKey) {
		log.info("초대장 코드: {}에 대한 초대장을 조회합니다.", invitationKey);
		Invite invite = readOnlyRepositoryService.findByCode(invitationKey);
		readOnlyRepositoryService.checkValidation(invite);
		return invite;
	}
}
