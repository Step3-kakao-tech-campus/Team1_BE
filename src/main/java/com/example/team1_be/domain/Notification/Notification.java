package com.example.team1_be.domain.Notification;

import com.example.team1_be.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "Notification_tb")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int id;

    @Column(length = 200, nullable = false)
    @Size(max = 200)
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private NotificationType type;

    @NotNull
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
//    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Notification(int id, String content, NotificationType type, Boolean isRead, User user) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.isRead = isRead;
        this.user = user;
    }
}
