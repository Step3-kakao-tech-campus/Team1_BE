package com.example.team1_be.domain.Group.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Create {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private String marketName;
        private String marketNumber;
        private String mainAddress;
        private String detailAddress;
    }
}
