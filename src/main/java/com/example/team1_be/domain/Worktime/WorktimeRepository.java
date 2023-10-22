package com.example.team1_be.domain.Worktime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    @Query("select w " +
            "from Worktime w " +
            "where w.day.week.startDate = :date " +
            "and w.day.week.schedule.id = :scheduleId")
    List<Worktime> findByStartDateAndScheduleId(@Param("date")LocalDate date, @Param("scheduleId")Long scheduleId);

    @Query("select w " +
            "from Worktime w " +
            "where w.day.week.startDate = :date " +
            "and w.day.dayOfWeek = :dayOfWeek " +
            "and w.day.week.schedule.id = :scheduleId " +
            "and w.day.week.status = 'ENDED'")
    List<Worktime> findBySpecificDateAndScheduleId(@Param("date") LocalDate date,
                                                   @Param("dayOfWeek")int dayOfWeek,
                                                   @Param("scheduleId") Long scheduleId);
}
