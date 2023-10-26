package com.example.team1_be.domain.Schedule;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team1_be.domain.Group.Group;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	Optional<Schedule> findByGroup(Group group);
}
