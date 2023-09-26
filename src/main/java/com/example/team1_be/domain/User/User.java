package com.example.team1_be.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.GenerationType;
import javax.validation.constraints.Size;

@Getter
@Entity
@RequiredArgsConstructor
public class User {
    private final int MIN_NAME_LENGHT = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int id;
    @Column(length = 30, nullable = false)
    @Size(min = 2, max = 30)
    private String name;
    @Column(length = 13, nullable = false)
    @Size(min = 13, max = 13)
    private String phoneNumber;

    @Builder
    public User(int id, String name, String phoneNumber) {
        if (name.length() < MIN_NAME_LENGHT)
            throw new IllegalArgumentException("이름은 2글자 미만일 수 없습니다.");

        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
