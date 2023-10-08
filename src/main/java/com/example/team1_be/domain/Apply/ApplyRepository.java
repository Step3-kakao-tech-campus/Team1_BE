package com.example.team1_be.domain.Apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query("select a " +
            "from Apply a " +
            "where a.worktime.id = :worktimeId")
    List<Apply> findappliesByWorktimeId(@Param("worktimeId") Long worktimeId);
}
