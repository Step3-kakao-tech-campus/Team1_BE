package com.example.team1_be.domain.User.UnfinishedUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnfinishedUserRepository extends JpaRepository<UnfinishedUser, Long> {
	Optional<UnfinishedUser> findByCode(String code);
}
