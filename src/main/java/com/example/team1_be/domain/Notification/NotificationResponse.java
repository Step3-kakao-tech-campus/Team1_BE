package com.example.team1_be.domain.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 알림 조회 Request DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Notice {
        private String date;                // 알림 보낸 시각
        private String content;             // 알림 내용
        private String notificationType;    // 알림 타입
    }

    private List<Notice> notifications;     // 알림 목록
}

