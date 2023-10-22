package com.example.team1_be.domain.Worktime;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.utils.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Worktime extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private String title;

    @NotNull
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    @NotNull
    private Day day;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "worktime")
    private List<Apply> applyList;

    @Builder
    public Worktime(Long id, LocalTime startTime, LocalTime endTime, String title, int amount, Day day) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.amount = amount;
        this.day = day;
    }
}
