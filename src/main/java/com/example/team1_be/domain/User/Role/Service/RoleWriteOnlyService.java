package com.example.team1_be.domain.User.Role.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.User.Role.Role;
import com.example.team1_be.domain.User.Role.RoleRepository;
import com.example.team1_be.domain.User.Role.RoleType;
import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleWriteOnlyService {
	private final RoleRepository repository;

	private void createRole(User user, RoleType roleType) {
		log.info(roleType + " 역할을 생성하고 저장합니다.");
		repository.save(Role.builder()
			.roleType(roleType)
			.user(user)
			.build());
		log.info(roleType + " 역할 생성 및 저장이 완료되었습니다.");
	}

	public void createAdmin(User user) {
		createRole(user, RoleType.ROLE_ADMIN);
	}

	public void createMember(User user) {
		createRole(user, RoleType.ROLE_MEMBER);
	}
}
