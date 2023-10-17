package com.example.team1_be.domain.Apply;

import com.example.team1_be.domain.Worktime.Worktime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query("select a " +
            "from Apply a " +
            "where a.worktime.id = :worktimeId")
    List<Apply> findappliesByWorktimeId(@Param("worktimeId") Long worktimeId);

    @Query("select a " +
            "from Apply a " +
            "where a.status = :status")
    List<Apply> findAppliesByStatus(@Param("status") ApplyStatus status);

    @Query("SELECT a.worktime " +
            "FROM Apply a " +
            "WHERE a.worktime.day.week.startDate between :requestMonth and :toMonth " +
            "AND a.member.id = :memberId " +
            "AND a.status = :status")
    List<Worktime> findByYearMonthAndStatusAndMemberId(@Param("requestMonth") LocalDate requestMonth,
                                                       @Param("toMonth") LocalDate toMonth,
                                                       @Param("memberId") Long id,
                                                       @Param("status") ApplyStatus status);
}
