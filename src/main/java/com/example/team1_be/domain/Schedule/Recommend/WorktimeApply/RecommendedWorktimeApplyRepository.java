package com.example.team1_be.domain.Schedule.Recommend.WorktimeApply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendedWorktimeApplyRepository extends JpaRepository<RecommendedWorktimeApply, Long> {

    @Query("delete from RecommendedWorktimeApply r where r.recommendedWeeklySchedule.id in :ids")
    void deleteByWeeklyScheduleIds(@Param("ids") List<Long> ids);
}
