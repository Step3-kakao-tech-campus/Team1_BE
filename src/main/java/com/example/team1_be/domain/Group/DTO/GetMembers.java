package com.example.team1_be.domain.Group.DTO;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class GetMembers {
    @Getter
    public static class Response {
        private String groupName;
        private String userName;
        private List<MemberInfo> members;

        public Response(Group group, User user, List<User> users) {
            this.groupName = group.getName();
            this.userName = user.getName();
            this.members = users.stream()
                    .map(member -> new MemberInfo(member))
                    .collect(Collectors.toList());
        }

        @Getter
        private class MemberInfo {
            private Long memberId;
            private String name;
            private Boolean isAdmin;

            private MemberInfo(User member) {
                this.memberId = member.getId();
                this.name = member.getName();
                this.isAdmin = member.getIsAdmin();
            }
        }
    }
}
