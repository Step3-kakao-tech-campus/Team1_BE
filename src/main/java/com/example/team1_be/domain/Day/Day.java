package com.example.team1_be.domain.Day;

import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "days")
public class Day extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(1)
    @Max(7)
    private int dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Week week;

    @Builder
    public Day(Long id, int dayOfWeek, Week week) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.week = week;
    }
}
