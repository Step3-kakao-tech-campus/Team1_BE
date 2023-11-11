package com.example.team1_be.domain.User.Role;

public enum RoleType {
	ROLE_ADMIN,
	ROLE_MEMBER;

	public String getAuth() {
		return name().replaceFirst("ROLE_", "");
	}
}
