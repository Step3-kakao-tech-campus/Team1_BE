package com.example.team1_be.domain.User.UnfinishedUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnfinishedUserRepository extends JpaRepository<UnfinishedUser, Long> {
    Optional<UnfinishedUser> findByCode(String code);
}
