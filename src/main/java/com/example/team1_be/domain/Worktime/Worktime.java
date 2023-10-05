package com.example.team1_be.domain.Worktime;

import com.example.team1_be.domain.Day.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Worktime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Day day;

    @Builder
    public Worktime(Long id, LocalDateTime startTime, LocalDateTime endTime, int amount, Day day) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        this.day = day;
    }
}
