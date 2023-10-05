package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Group group;

    @Builder
    public Schedule(int id, Group group) {
        this.id = id;
        this.group = group;
    }
}
