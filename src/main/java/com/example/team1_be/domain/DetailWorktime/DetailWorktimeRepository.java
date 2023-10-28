package com.example.team1_be.domain.DetailWorktime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.team1_be.domain.Week.WeekRecruitmentStatus;

public interface DetailWorktimeRepository extends JpaRepository<DetailWorktime, Long> {
	@Query("select dw "
		+ "from DetailWorktime dw "
		+ "where dw.worktime.id = :worktimeId "
		+ "and dw.dayOfWeek = :dayOfWeek")
	Optional<DetailWorktime> findByWorktimeAndStatus(@Param("worktimeId") Long id,
		@Param("dayOfWeek") DayOfWeek dayOfWeek);

	@Query("select dw "
		+ "from DetailWorktime dw "
		+ "where dw.worktime.week.group.id = :groupId "
		+ "and dw.date = :date "
		+ "and dw.worktime.week.status = :status ")
	List<DetailWorktime> findByGroupAndDatesAndStatus(@Param("groupId") Long groupId,
		@Param("date") LocalDate startDate,
		@Param("status") WeekRecruitmentStatus weekRecruitmentStatus);

	@Query("select dw "
		+ "from DetailWorktime dw "
		+ "where dw.worktime.week.startDate = :date "
		+ "and dw.worktime.week.group.id = :groupId")
	List<DetailWorktime> findByStartDateAndGroup(@Param("date") LocalDate date, @Param("groupId") Long id);

	@Query("select dw "
		+ "from DetailWorktime dw "
		+ "where dw.worktime.week.group.id = :groupId "
		+ "and dw.date = :date")
	List<DetailWorktime> findByGroupAndDate(@Param("groupId") Long groupId, @Param("date") LocalDate date);

	@Query("select dw "
		+ "from DetailWorktime dw "
		+ "where dw.worktime.week.startDate = :date "
		+ "and dw.worktime.id in (:ids)")
	List<DetailWorktime> findByStartDateAndWorktimes(@Param("date") LocalDate date, @Param("ids") List<Long> ids);
}
