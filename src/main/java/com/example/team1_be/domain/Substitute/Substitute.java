package com.example.team1_be.domain.Substitute;

import com.example.team1_be.domain.Apply.Apply;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "Substitute_tb")
public class Substitute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substitute_id")
    private int id;

    @Size(max = 255)
    @NotNull
    private String content;

    @NotNull
    private Boolean adminApprove;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Apply applicant;

    @OneToOne(fetch = FetchType.LAZY)
    private Apply receptionist;

    @Builder
    public Substitute(int id, String content, Boolean adminApprove, Apply applicant, Apply receptionist) {
        this.id = id;
        this.content = content;
        this.adminApprove = adminApprove;
        this.applicant = applicant;
        this.receptionist = receptionist;
    }
}
