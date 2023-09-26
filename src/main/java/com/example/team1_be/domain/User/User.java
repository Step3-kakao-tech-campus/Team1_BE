package com.example.team1_be.domain.User;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
public class User {
    private final int MIN_NAME_LENGHT = 2;
    @Id
    @Column(name = "USER_ID")
    private int id;
    private String name;
    private String phoneNumber;

    public User(int id, String name, String phoneNumber) {
        if (name.length() < MIN_NAME_LENGHT)
            throw new IllegalArgumentException("이름은 2글자 미만일 수 없습니다.");

        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
