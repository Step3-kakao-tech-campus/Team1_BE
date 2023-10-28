package com.example.team1_be.domain.Apply;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.team1_be.domain.User.User;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
	@Query("select a " +
		"from Apply a " +
		"where a.detailWorktime.worktime.id in (:detailWorktimeIds)")
	List<Apply> findAppliesByWorktimeIds(@Param("detailWorktimeIds") List<Long> detailWorktimeIds);

	@Query("select a "
		+ "from Apply a "
		+ "where a.user.id = :userId "
		+ "and a.detailWorktime.date = :date "
		+ "and a.status = :status")
	List<Apply> findByUserAndDateAndStatus(@Param("userId") Long userId, @Param("date") LocalDate date,
		@Param("status") ApplyStatus status);

	@Query("select a.user "
		+ "from Apply a "
		+ "where a.detailWorktime.id = :detailWorktimeId "
		+ "and a.status = :status ")
	List<User> findUsersByWorktimeAndApplyStatus(@Param("detailWorktimeId") Long id,
		@Param("status") ApplyStatus status);
}
