package com.example.team1_be.domain.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class UserRequest {
    @Getter
    public static class JoinDTO {

        @NotEmpty
        private String accessToken;

        @NotEmpty
        private String name;
    }

    @Getter
    public static class LoginDTO {

        @NotEmpty
        private String accessToken;
    }
}