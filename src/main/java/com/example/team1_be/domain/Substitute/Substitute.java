package com.example.team1_be.domain.Substitute;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.utils.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Substitute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    public Substitute(Long id, String content, Boolean adminApprove, Apply applicant, Apply receptionist) {
        this.id = id;
        this.content = content;
        this.adminApprove = adminApprove;
        this.applicant = applicant;
        this.receptionist = receptionist;
    }
}
