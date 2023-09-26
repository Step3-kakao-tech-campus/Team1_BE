    package com.example.team1_be.domain.User;

public class User {
    private final int MIN_NAME_LENGHT = 2;
    private final String name;
    private final String phoneNumber;

    public User(String name, String phoneNumber) {
        if (name.length() < MIN_NAME_LENGHT)
            throw new IllegalArgumentException("이름은 2글자 미만일 수 없습니다.");

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
