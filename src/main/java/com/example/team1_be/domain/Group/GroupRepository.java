package com.example.team1_be.domain.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("select m.group " +
            "from Member m " +
            "where m.user.id = :userId")
    Optional<Group> findByUser(@Param("userId") Long userId);
}
