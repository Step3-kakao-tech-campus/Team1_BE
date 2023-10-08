package com.example.team1_be.domain.Worktime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorktimeRepository extends JpaRepository<Worktime, Long> {
    @Query("select w " +
            "from Worktime w " +
            "where w.day.id = :dayId")
    List<Worktime> findByDayId(@Param("dayId")Long id);


    @Query("select sum (w.amount) " +
            "from Worktime w " +
            "where w.day.week.id = :weekId")
    Long findCountByWeekId(@Param("weekId") long weekId);
}
