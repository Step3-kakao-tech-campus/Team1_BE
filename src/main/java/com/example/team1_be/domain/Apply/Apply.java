package com.example.team1_be.domain.Apply;

import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Worktime.Worktime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ApplyType state;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Worktime worktime;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    @Builder
    public Apply(int id, ApplyType state, Worktime worktime, Member member) {
        this.id = id;
        this.state = state;
        this.worktime = worktime;
        this.member = member;
    }
}
