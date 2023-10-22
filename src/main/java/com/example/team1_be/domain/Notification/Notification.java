package com.example.team1_be.domain.Notification;

import com.example.team1_be.domain.User.User;
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
@Table
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @NotNull
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private NotificationType type;

    @NotNull
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Builder
    public Notification(Long id, String content, NotificationType type, Boolean isRead, User user) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.isRead = isRead;
        this.user = user;
    }
}
