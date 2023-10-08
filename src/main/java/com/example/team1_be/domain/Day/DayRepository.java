package com.example.team1_be.domain.Day;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {
    @Query("select d " +
            "from Day d " +
            "where d.week.id = :weekId")
    List<Day> findByWeekId(@Param("weekId")Long weekId);
}
