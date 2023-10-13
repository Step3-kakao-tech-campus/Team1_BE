package com.example.team1_be.domain.Group.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GetInvitation {
    @Getter
    public static class Response {
        private String invitationKey;

        public Response(String invitationKey) {
            this.invitationKey = invitationKey;
        }
    }
}
