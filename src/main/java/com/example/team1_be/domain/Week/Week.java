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
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private WeekType status;

    @NotNull
    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Schedule schedule;

    @Builder
    public Week(Long id, WeekType status, LocalDateTime startTime, Schedule schedule) {
        this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.schedule = schedule;
    }
}
