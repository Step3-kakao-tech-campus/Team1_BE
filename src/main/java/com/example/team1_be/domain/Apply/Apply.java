package com.example.team1_be.domain.Apply;

import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Apply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ApplyStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktime_id")
    private Worktime worktime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apply")
    private List<RecommendedWorktimeApply> recommendedWorktimeApplies;

    @Builder
    public Apply(Long id, ApplyStatus status, Worktime worktime, User user, List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
        this.id = id;
        this.status = status;
        this.worktime = worktime;
        this.user = user;
        this.recommendedWorktimeApplies = recommendedWorktimeApplies;
    }

    public Apply updateStatus(ApplyStatus status) {
        return Apply.builder()
                .id(this.id)
                .status(status)
                .user(this.user)
                .worktime(this.worktime)
                .build();
    }
}
