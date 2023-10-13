package com.example.team1_be.domain.Group.DTO;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Member.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class GetMembers {
    @Getter
    public static class Response {
        private String groupName;
        private List<MemberInfo> members;

        public Response(Group group, List<Member> members) {
            this.groupName = group.getName();
            this.members = members.stream()
                    .map(member -> new MemberInfo(member))
                    .collect(Collectors.toList());
        }

        @Getter
        private class MemberInfo {
            private Long memberId;
            private String name;
            private Boolean isAdmin;

            private MemberInfo(Member member) {
                this.memberId = member.getId();
                this.name = member.getUser().getName();
                this.isAdmin = member.getIsAdmin();
            }
        }
    }
}
