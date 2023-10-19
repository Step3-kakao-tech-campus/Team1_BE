package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendedWeeklyScheduleRepository extends JpaRepository<RecommendedWeeklySchedule, Long> {
    List<RecommendedWeeklySchedule> findByUser(User user);

    void deleteByUser(User user);
}
