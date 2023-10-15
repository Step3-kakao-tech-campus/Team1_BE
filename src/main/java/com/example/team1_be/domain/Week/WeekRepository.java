package com.example.team1_be.domain.Week;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface WeekRepository extends JpaRepository<Week, Long> {

    @Query("select w " +
            "from Week w " +
            "where w.schedule.id = :scheduleId" +
            " and w.startDate = :startDate " +
            "and w.status = :status")
    Optional<Week> findByScheduleIdStartDateAndAndStatus(@Param("scheduleId") Long scheduleId, @Param("startDate")LocalDate startDate, @Param("status") WeekRecruitmentStatus status);
}
