package com.example.team1_be.domain.Member;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "Member_tb")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column(nullable = false)
    @NotNull
    private Boolean isAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Group group;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Builder
    public Member(int id, Boolean isAdmin, Group group, User user) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.group = group;
        this.user = user;
    }
}
