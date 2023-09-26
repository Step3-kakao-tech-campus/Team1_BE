package com.example.team1_be.domain.Member;


import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private int id;
    @NotNull
    private boolean isAdmin;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @Builder
    public Member(int id, boolean isAdmin, User user, Group group) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.user = user;
        this.group = group;
    }
}
