package com.example.team1_be.domain.Group.Invite.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.Group.Invite.InviteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class InviteWriteRepositoryService {
	private final InviteRepository repository;

	@Transactional
	public void createInvite(Invite invite) {
		log.info("초대를 생성합니다.");
		repository.save(invite);
		log.info("초대 생성이 완료되었습니다.");
	}

	@Transactional
	public void renewInvitation(Invite invite) {
		log.info("초대를 갱신합니다.");
		invite.renew();
		createInvite(invite);
		log.info("초대 갱신이 완료되었습니다.");
	}
}
