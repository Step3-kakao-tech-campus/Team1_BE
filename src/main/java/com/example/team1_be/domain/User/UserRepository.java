package com.example.team1_be.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.team1_be.domain.Group.Group;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByKakaoId(Long kakaoId);

	@Query("select u.group " +
		"from User u " +
		"where u.id = :userId")
	Optional<Group> findGroupByUser(@Param("userId") Long userId);
}
