package com.example.team1_be.utils.security.auth.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.team1_be.domain.User.Role.Role;
import com.example.team1_be.domain.User.Role.RoleType;
import com.example.team1_be.domain.User.User;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {
	private final User user;
	private final Collection<SimpleGrantedAuthority> authorities;

	public CustomUserDetails(User user) {
		this.user = user;
		this.authorities = new ArrayList<SimpleGrantedAuthority>();

		for (Role roles : user.getRoles()) {
			RoleType role = roles.getRoleType();
			this.authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
