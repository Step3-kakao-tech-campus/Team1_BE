package com.example.team1_be.domain.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그룹 생성하기 Response DTO
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateResponse {
    private Long groupId;       // 그룹 id
    private String groupName;   // 그룹 이름
}

