package com.example.team1_be.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.example.team1_be.domain.User.Role.Role;
import com.example.team1_be.domain.User.Role.Roles;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;

public class WithMockCustomMemberUserSecurityContextFactory
	implements WithSecurityContextFactory<WithMockCustomMemberUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomMemberUser annotation) {
		final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		final Role memberRole = Role.builder()
			.role(Roles.ROLE_ADMIN)
			.build();
		final Role userRole = Role.builder()
			.role(Roles.ROLE_USER)
			.build();
		final Set<Role> roleSet = new HashSet<>(List.of(new Role[] {userRole, memberRole}));
		final User user = User.builder()
			.id(Long.valueOf(annotation.userId()))
			.name(annotation.username())
			.kakaoId(Long.valueOf(annotation.kakaoId()))
			.phoneNumber(annotation.phoneNumber())
			.isAdmin(Boolean.valueOf(annotation.isAdmin()))
			.roles(roleSet)
			.build();

		final CustomUserDetails customUserDetails = new CustomUserDetails(user);
		final UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(customUserDetails,
				"",
				customUserDetails.getAuthorities());

		securityContext.setAuthentication(authenticationToken);
		return securityContext;
	}
}
