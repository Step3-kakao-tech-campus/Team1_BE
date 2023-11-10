package com.example.team1_be.domain.Group.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {
	private final UserService userService;
	private final InviteService inviteService;
	private final GroupReadOnlyRepositoryService readOnlyRepositoryService;
	private final GroupWriteOnlyRepositoryService writeOnlyRepositoryService;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void create(User user, Create.Request request) {
		Group group = request.toGroup();
		writeOnlyRepositoryService.creatGroup(group);
		logger.info("그룹 생성 됨, id  : {}", group.getId());

		inviteService.createInviteWithGroup(group);
		logger.info("초대장 생성");

		userService.updateGroup(user, group);
		logger.info("그룹 가입함");
	}

	public void invitationAccept(User user, InvitationAccept.Request request) {
		Invite invite = inviteService.findInvitation(request.getInvitationKey());
		logger.info("초대장 조회");

		Group group = invite.getGroup();
		userService.updateGroup(user, group);
		logger.info("그룹 가입");
	}

	public GetMembers.Response getMembers(User user) {
		Group group = userService.findGroupByUser(user);
		List<User> users = group.getUsers();
		logger.info("그룹원 조회");

		return new GetMembers.Response(group, user, users);
	}
}
