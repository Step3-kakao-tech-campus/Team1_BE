package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByGroup(Group group);
}
