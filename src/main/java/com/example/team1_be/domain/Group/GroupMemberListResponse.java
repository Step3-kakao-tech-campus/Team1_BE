package com.example.team1_be.domain.Group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 그룹원 조회 Response DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberListResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Member {
        private Integer memberId;
        private String name;
    }

    private List<Member> members;
}
