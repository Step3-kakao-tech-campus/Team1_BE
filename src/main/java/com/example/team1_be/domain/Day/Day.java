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
@Table(name = "Day_tb")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Weekday weekday;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Week week;

    @Builder
    public Day(int id, Weekday weekday, Week week) {
        this.id = id;
        this.weekday = weekday;
        this.week = week;
    }
}
