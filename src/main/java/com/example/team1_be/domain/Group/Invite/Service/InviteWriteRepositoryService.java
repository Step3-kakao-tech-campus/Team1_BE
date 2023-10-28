package com.example.team1_be.domain.Group.Invite.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class InviteWriteRepositoryService {
	private final InviteRepository repository;

	@Transactional
	public void createInvite(Invite invite) {
		repository.save(invite);
	}

	@Transactional
	public void renewInvitation(Invite invite) {
		invite.renew();
		createInvite(invite);
	}
}
