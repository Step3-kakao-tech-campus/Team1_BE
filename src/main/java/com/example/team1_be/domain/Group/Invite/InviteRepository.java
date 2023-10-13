package com.example.team1_be.domain.Group.Invite;

import com.example.team1_be.domain.Group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<Invite> findByCode(String code);
    Optional<Invite> findByGroup(Group group);
}
