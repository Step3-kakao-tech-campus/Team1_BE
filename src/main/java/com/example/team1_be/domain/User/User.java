package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Member.Member;
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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long kakaoId;

    @Size(min = 2, max = 10)
    @Column(nullable = false)
    private String name;

    @Size(min = 13, max = 13)
    private String phoneNumber;

    @NotNull
    private Boolean isAdmin;

    @OneToOne(mappedBy = "user")
    private Member member;

    @Builder
    public User(Long id, Long kakaoId, String name, String phoneNumber, Boolean isAdmin, Member member) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.member = member;
    }
}
