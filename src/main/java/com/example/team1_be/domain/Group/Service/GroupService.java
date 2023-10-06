package com.example.team1_be.domain.Group.Service;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final InviteRepository inviteRepository;
    private final GroupRepository groupRepository;


    public void generateInviteCode(){
         String code = UUID.randomUUID().toString();

    }

}
