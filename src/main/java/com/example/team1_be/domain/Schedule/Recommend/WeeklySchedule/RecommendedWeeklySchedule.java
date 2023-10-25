package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.User.User;
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
public class RecommendedWeeklySchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recommendedWeeklySchedule", orphanRemoval = true)
    private List<RecommendedWorktimeApply> recommendedWorktimeApplies;

    @Builder
    public RecommendedWeeklySchedule(Long id, User user, List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
        this.id = id;
        this.user = user;
        this.recommendedWorktimeApplies = recommendedWorktimeApplies;
    }
}
