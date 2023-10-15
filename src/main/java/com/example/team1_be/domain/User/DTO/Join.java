package com.example.team1_be.domain.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class Join {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "kakaoId가 누락되었습니다.")
        private Long kakaoId;

        @NotBlank(message = "userName이 누락되었습니다.")
        private String userName;

        @NotBlank(message = "isAdmin이 누락되었습니다.")
        private boolean isAdmin;
    }
}
