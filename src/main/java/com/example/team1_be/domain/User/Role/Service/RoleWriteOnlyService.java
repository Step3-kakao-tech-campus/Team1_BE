package com.example.team1_be.domain.User.Role.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.User.Role.Role;
import com.example.team1_be.domain.User.Role.RoleRepository;
import com.example.team1_be.domain.User.Role.Roles;
import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleWriteOnlyService {
	private final RoleRepository repository;

	public void createAdmin(User user) {
		repository.save(Role.builder()
			.role(Roles.ADMIN)
			.user(user)
			.build());
	}

	public void createMember(User user) {
		repository.save(Role.builder()
			.role(Roles.MEMBER)
			.user(user)
			.build());
	}
}
