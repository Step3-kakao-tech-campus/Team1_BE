package com.example.team1_be.domain.Group.Invite;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.utils.audit.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Invite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String code;

    private LocalDateTime renewedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Group group;

    @Builder
    public Invite(Long id, String code, LocalDateTime renewedAt, Group group) {
        this.id = id;
        this.code = code;
        this.renewedAt = renewedAt;
        this.group = group;
    }

    public Invite renew() {
        return Invite.builder()
                .id(this.getId())
                .group(this.getGroup())
                .code(this.getCode())
                .renewedAt(LocalDateTime.now())
                .build();
    }
}
