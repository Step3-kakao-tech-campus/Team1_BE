package com.example.team1_be.domain.Group;

import lombok.Data;

/**
 * 그룹 초대장 접속 및 수락 Request DTO
 */
@Data
public class GroupInvitationAcceptRequest {
    String invitationKey;      // 초대 key
}
