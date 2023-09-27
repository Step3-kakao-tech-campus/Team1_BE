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
@Table(name = "Worktime_tb")
public class Worktime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worktime_id")
    private int id;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime startTime;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime endTime;

    @Column(nullable = false)
    @NotNull
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Day day;

    @Builder
    public Worktime(int id, LocalDateTime startTime, LocalDateTime endTime, int amount, Day day) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        this.day = day;
    }
}
