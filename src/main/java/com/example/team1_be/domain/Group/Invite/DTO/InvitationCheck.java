package com.example.team1_be.domain.Group.Invite.DTO;

import com.example.team1_be.domain.Group.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InvitationCheck {

    @Getter
    public static class Response {
        private String marketName;

        @Builder
        public Response(Group group) {
            this.marketName = group.getName();
        }
    }
}
