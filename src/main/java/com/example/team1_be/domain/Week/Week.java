package com.example.team1_be.domain.Week;

import com.example.team1_be.domain.Schedule.Schedule;
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
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_id")
    private int id;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Schedule schedule;

    @Builder
    public Week(int id, LocalDateTime startTime, Schedule schedule) {
        this.id = id;
        this.startTime = startTime;
        this.schedule = schedule;
    }
}
