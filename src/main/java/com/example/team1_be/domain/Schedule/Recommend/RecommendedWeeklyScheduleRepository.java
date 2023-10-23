package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendedWeeklyScheduleRepository extends JpaRepository<RecommendedWeeklySchedule, Long> {
    @Query("select r " +
            "from RecommendedWeeklySchedule r " +
            "join fetch r.recommendedWorktimeApplies " +
            "where r.user.id = :userId")
    List<RecommendedWeeklySchedule> findByUser(@Param("userId") Long userId);
}
