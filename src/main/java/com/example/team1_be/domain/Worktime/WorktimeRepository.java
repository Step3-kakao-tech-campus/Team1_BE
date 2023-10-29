package com.example.team1_be.domain.Worktime;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.team1_be.domain.Week.Week;

public interface WorktimeRepository extends JpaRepository<Worktime, Long> {
	@Query("select w "
		+ "from Worktime w "
		+ "where w.week.group.id = :groupId "
		+ "and w.week.startDate = :date")
	List<Worktime> findByDate(@Param("groupId") Long groupId, @Param("date") LocalDate date);

	List<Worktime> findByWeek(Week week);
}
