package com.example.team1_be.domain.Group.Invite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team1_be.domain.Group.Group;

public interface InviteRepository extends JpaRepository<Invite, Long> {
	Optional<Invite> findByCode(String code);

	Optional<Invite> findByGroup(Group group);
}
