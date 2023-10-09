package com.example.team1_be.domain.User;

import lombok.*;

import javax.validation.constraints.NotEmpty;

public class UserRequest {
    @Getter
    @NoArgsConstructor
    public static class JoinDTO {

        @NotEmpty
        private String accessToken;

        @NotEmpty
        private String name;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginDTO {

        @NotEmpty
        private String accessToken;
    }
}