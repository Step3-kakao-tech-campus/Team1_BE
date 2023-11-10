package com.example.team1_be.utils.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;

@Component
public class UserAuditorAware implements AuditorAware<Long> {
	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null ||
			!authentication.isAuthenticated() ||
			authentication.getPrincipal() == "anonymousUser") {
			return Optional.empty();
		}
		Optional<Long> id = Optional.of(((CustomUserDetails)authentication.getPrincipal()).getUser().getId());
		return id;
	}
}
