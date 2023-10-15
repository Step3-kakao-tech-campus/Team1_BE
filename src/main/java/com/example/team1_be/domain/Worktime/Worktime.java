package com.example.team1_be.domain.Worktime;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @NotNull
    private Day day;

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
