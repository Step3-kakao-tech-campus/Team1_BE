package com.example.team1_be.domain.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class UserResponse {

    @Getter
    @Setter
    public static class KakaoLoginDTO {
        @NotEmpty
        private String accessToken;

        @NotEmpty
        private Boolean alreadyJoin;

        public KakaoLoginDTO(String accessToken, Boolean alreadyJoin) {
            this.accessToken = accessToken;
            this.alreadyJoin = alreadyJoin;
        }
    }
}
