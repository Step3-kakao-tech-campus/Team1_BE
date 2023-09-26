package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID")
    private int id;

    @Id
    @OneToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @Builder
    public Schedule(int id, Group group) {
        this.id = id;
        this.group = group;
    }
}
