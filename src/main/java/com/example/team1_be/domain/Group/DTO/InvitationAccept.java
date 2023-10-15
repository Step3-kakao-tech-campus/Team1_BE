package com.example.team1_be.domain.Group.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class InvitationAccept {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String invitationKey;
    }
}
