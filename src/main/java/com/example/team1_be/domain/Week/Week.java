package com.example.team1_be.domain.Week;

import com.example.team1_be.domain.Schedule.Schedule;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@RequiredArgsConstructor
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WEEK_ID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Builder
    public Week(int id, Schedule schedule, LocalDateTime startTime){
        this.id = id;
        this.schedule = schedule;
        this.startTime = startTime;
    }
}
