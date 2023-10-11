package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Group group;

    @Builder
    public Schedule(Long id, Group group) {
        this.id = id;
        this.group = group;
    }
}
