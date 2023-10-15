package com.example.team1_be.domain.Week;

import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Week extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private WeekRecruitmentStatus status;

    @NotNull
    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Schedule schedule;

    @Builder
    public Week(Long id, WeekRecruitmentStatus status, LocalDate startDate, Schedule schedule) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.schedule = schedule;
    }
}
