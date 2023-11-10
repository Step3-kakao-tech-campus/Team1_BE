package com.example.team1_be.domain.User.Role.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
	private final RoleReadOnlyService readOnlyService;
	private final RoleWriteOnlyService writeOnlyService;

	public void createRole(User user, boolean isAdmin) {
		if (isAdmin) {
			writeOnlyService.createAdmin(user);
		} else {
			writeOnlyService.createMember(user);
		}
	}
}
