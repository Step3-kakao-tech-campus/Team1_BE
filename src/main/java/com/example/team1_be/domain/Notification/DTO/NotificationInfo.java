package com.example.team1_be.domain.Notification.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 알림 조회 Request DTO
 */
public class NotificationInfo {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private List<Notice> notifications;     // 알림 목록

        @Builder
        public Response(List<Notice> notifications) {
            this.notifications = notifications;
        }

        @Getter
        @NoArgsConstructor
        public class Notice {
            private String date;                // 알림 보낸 시각
            private String content;             // 알림 내용
            private String notificationType;    // 알림 타입

            @Builder
            public Notice(String date, String content, String notificationType) {
                this.date = date;
                this.content = content;
                this.notificationType = notificationType;
            }
        }
    }
}

