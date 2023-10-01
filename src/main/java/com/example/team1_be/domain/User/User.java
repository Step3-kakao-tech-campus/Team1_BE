package com.example.team1_be.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "User_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotNull
    private Long kakaoId;

    @Column(length = 10, nullable = false)
    @Size(min = 2, max = 10)
    private String name;

    @Column(length = 13, nullable = false)
    @Size(min = 13, max = 13)
    private String phoneNumber;

    @Builder
    public User(int id, Long kakaoId, String name, String phoneNumber) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
