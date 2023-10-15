package com.example.team1_be.domain.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class Login {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "code가 누락되었습니다.")
        private String code;
    }

    @Getter
    public static class Response {
        private String userName;
        private boolean isAdmin;
        private String groupName;

        public Response(String userName, boolean isAdmin, String groupName) {
            this.userName = userName;
            this.isAdmin = isAdmin;
            this.groupName = groupName;
        }

    }

}
