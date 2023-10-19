package com.example.team1_be.domain.Schedule.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendedWorktimeApplyRepository extends JpaRepository<RecommendedWorktimeApply, Long> {

}
