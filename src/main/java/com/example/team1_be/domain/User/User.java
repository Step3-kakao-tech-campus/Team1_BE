package com.example.team1_be.domain.User;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.utils.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long kakaoId;

    @Size(min = 2, max = 10)
    @Column(nullable = false)
    private String name;

    @Size(min = 13, max = 13)
    private String phoneNumber;

    @NotNull
    private Boolean isAdmin;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Group group;

    @Builder
    public User(Long id, Long kakaoId, String name, String phoneNumber, Boolean isAdmin, Member member, Group group) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
        this.member = member;
        this.group = group;
    }
    }
}
