package com.example.team1_be.domain.Notification;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 알림 조회 Request DTO
 */
@Data
public class NotificationResponse {

    @Getter @Setter
    private class Notice {
    LocalDateTime date;             // 알림 보낸 시각
        String content;             // 알림 내용
        String notificationType;    // 알림 타입
    }

    List<Notice> notifications;     // 알림 목록
}

