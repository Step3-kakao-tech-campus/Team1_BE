package com.example.team1_be.domain.Apply;

import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Schedule.Recommend.RecommendedWorktimeApply;
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
    @NotNull
    private Worktime worktime;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apply")
    private List<RecommendedWorktimeApply> recommendedWorktimeApplies;

    @Builder
    public Apply(Long id, ApplyStatus status, Worktime worktime, Member member, List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
        this.id = id;
        this.status = status;
        this.worktime = worktime;
        this.member = member;
        this.recommendedWorktimeApplies = recommendedWorktimeApplies;
    }

    public Apply updateStatus(ApplyStatus status) {
        return Apply.builder()
                .id(this.id)
                .status(status)
                .worktime(this.worktime)
                .member(this.member)
                .build();
    }
}
