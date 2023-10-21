package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table
public class RecommendedWorktimeApply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    private Apply apply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendedWeeklySchedule_id")
    private RecommendedWeeklySchedule recommendedWeeklySchedule;

    @Builder
    public RecommendedWorktimeApply(Long id, Apply apply, RecommendedWeeklySchedule recommendedWeeklySchedule) {
        this.id = id;
        this.apply = apply;
        this.recommendedWeeklySchedule = recommendedWeeklySchedule;
    }
}
