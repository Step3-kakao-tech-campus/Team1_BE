package com.example.team1_be.domain.Group;

import com.example.team1_be.domain.Group.DTO.Create;
import com.example.team1_be.domain.Group.DTO.GetMembers;
import com.example.team1_be.domain.Group.DTO.InvitationAccept;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteService;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserService;
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
    private final UserService userService;
    private final InviteService inviteService;

    private final GroupRepository groupRepository;

    @Transactional
    public void create(User user, Create.Request request) {
        if (!user.getIsAdmin()) {
            throw new CustomException("매니저 계정만 그룹을 생성할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        Group group = Group.builder()
                .name(request.getMarketName())
                .address(request.getMainAddress() + request.getDetailAddress())
                .businessNumber(request.getMarketNumber())
                .build();
        group = creatGroup(group);

        Invite invite = Invite.builder()
                .code(inviteService.generateInviteCode())
                .group(group)
                .build();
        inviteService.createInvite(invite);

        userService.updateGroup(user, group);
    }

    @Transactional
    public void invitationAccept(User user, InvitationAccept.Request request) {
        if (user.getIsAdmin()) {
            throw new CustomException("알바생 계정만 그룹에 가입할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        Invite invite = inviteService.findByCode(request.getInvitationKey());
        inviteService.checkValidation(invite);

        Group group = invite.getGroup();
        userService.updateGroup(user, group);
    }

    public GetMembers.Response getMembers(User user) {
        Group group = findByUser(user);
        List<User> users = group.getUsers();

        return new GetMembers.Response(group, user, users);
    }

    public Group findByUser(User user) {
        return groupRepository.findByUser(user.getId())
                .orElseThrow(() -> new CustomException("그룹에 가입되어있지 않습니다.", HttpStatus.FORBIDDEN));
    }

    @Transactional
    public Group creatGroup(Group group) {
        return groupRepository.save(group);
    }
}
