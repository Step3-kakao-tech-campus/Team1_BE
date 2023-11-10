package com.example.team1_be.domain.Group.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.Service.InviteService;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserService;
import com.example.team1_be.utils.errors.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
	private final UserService userService;
	private final InviteService inviteService;
	private final GroupReadOnlyRepositoryService readOnlyRepositoryService;
	private final GroupWriteOnlyRepositoryService writeOnlyRepositoryService;

	public void create(User user, Create.Request request) {
		Group group = userService.findGroupByUserOrNull(user);
		if (group != null) {
			log.error("그룹 생성 됨, id  : {}", group.getId());
			throw new ForbiddenException("이미 가입된 그룹이 있습니다.");
		}
		group = request.toGroup();
		writeOnlyRepositoryService.creatGroup(group);
		log.info("그룹 생성 됨, id  : {}", group.getId());

		inviteService.createInviteForGroup(group);
		log.info("초대장 생성");

		userService.updateGroup(user, group);
		log.info("그룹 가입함");
	}

	public void invitationAccept(User user, InvitationAccept.Request request) {
		Invite invite = inviteService.getInvitation(request.getInvitationKey());
		log.info("초대장 조회");

		Group group = invite.getGroup();
		userService.updateGroup(user, group);
		log.info("그룹 가입");
	}

	public GetMembers.Response getMembers(User user) {
		Group group = userService.findGroupByUserOrNull(user);
		if (group != null) {
			List<User> users = group.getUsers();
			return new GetMembers.Response(group, user, users);
		}
		group = Group.builder()
			.name(null)
			.build();
		return new GetMembers.Response(group, user, new ArrayList<>());
	}
}
