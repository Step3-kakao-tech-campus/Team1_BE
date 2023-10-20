package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.BaseEntity;
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

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Worktime worktime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    @NotNull
    private List<Apply> applies;

    @Builder
    public RecommendedWorktimeApply(Long id, Worktime worktime, List<Apply> applies) {
        this.id = id;
        this.worktime = worktime;
        this.applies = applies;
    }
}
