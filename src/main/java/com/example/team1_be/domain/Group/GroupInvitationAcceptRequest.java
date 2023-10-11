package com.example.team1_be.domain.Group;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그룹 초대장 접속 및 수락 Request DTO
 */
@Getter
@NoArgsConstructor
public class GroupInvitationAcceptRequest {
    private String invitationKey;      // 초대 key
}
