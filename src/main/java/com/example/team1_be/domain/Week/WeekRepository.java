package com.example.team1_be.domain.Week;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeekRepository extends JpaRepository<Week, Long> {

	@Query("select w " +
		"from Week w " +
		"where w.group.id = :groupId" +
		" and w.startDate = :startDate " +
		"and w.status = :status")
	Optional<Week> findByScheduleIdStartDateAndStatus(@Param("groupId") Long groupId,
		@Param("startDate") LocalDate startDate, @Param("status") WeekRecruitmentStatus status);

	@Query("select w " +
		"from Week w " +
		"where w.group.id = :groupId " +
		"and w.status = :weekStatus " +
		"order by w.id desc")
	Page<Week> findLatestByScheduleAndStatus(@Param("groupId") Long id,
		@Param("weekStatus") WeekRecruitmentStatus weekRecruitmentStatus,
		Pageable pageable);

	@Query("select w " +
		"from Week w " +
		"where w.group.id = :groupId " +
		"and w.startDate = :startDate")
	Optional<Week> findByGroupIdAndStartDate(@Param("groupId") Long id,
		@Param("startDate") LocalDate startDate);
}
