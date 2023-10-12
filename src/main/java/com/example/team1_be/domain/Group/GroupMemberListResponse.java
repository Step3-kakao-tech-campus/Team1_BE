package com.example.team1_be.domain.Group;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @Builder
    public static class Member {
        private Long memberId;
        private String name;
        @JsonProperty("isAdmin")
        private boolean isAdmin;
    }

    private String groupName;

    private List<Member> members;
}
