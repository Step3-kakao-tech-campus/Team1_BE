package com.example.team1_be.domain.Week;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeekRepository extends JpaRepository<Week, Long> {

    @Query("select w " +
            "from Week w " +
            "where w.schedule.id = :scheduleId" +
            " and w.startDate = :startDate " +
            "and w.status = :status")
    Optional<Week> findByScheduleIdStartDateAndStatus(@Param("scheduleId") Long scheduleId, @Param("startDate")LocalDate startDate, @Param("status") WeekRecruitmentStatus status);

    @Query("select w " +
            "from Week w " +
            "where w.startDate between :date and :toDate " +
            "and w.schedule.id = :scheduleId " +
            "and w.status = :status")
    List<Week> findByScheduleAndYearMonthAndStatus(@Param("date") LocalDate date,
                                                   @Param("toDate")LocalDate toDate,
                                                   @Param("scheduleId") Long scheduleId,
                                                   @Param("status") WeekRecruitmentStatus weekRecruitmentStatus);

    @Query("select w " +
            "from Week w " +
            "where w.schedule.id = :scheduleId " +
            "and w.status = :weekStatus " +
            "order by w.id desc")
    Page<Week> findByScheduleAndStatus(@Param("scheduleId") Long id,
                                       @Param("weekStatus") WeekRecruitmentStatus weekRecruitmentStatus,
                                       Pageable pageable);
}
