package com.example.team1_be.domain.Day;

import com.example.team1_be.domain.Week.Week;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
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
