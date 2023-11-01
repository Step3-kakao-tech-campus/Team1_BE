package com.example.team1_be.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.example.team1_be.domain.User.Role.Role;
import com.example.team1_be.domain.User.Role.Roles;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
		final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		final Role role = Role.builder()
			.role(Roles.ROLE_ADMIN)
			.build();
		final Set<Role> roleSet = new HashSet<>(Collections.singleton(role));
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
