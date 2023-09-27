package com.example.team1_be.domain.Notification;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("알림을 생성할 수 있다.")
    @Test
    void test1() {
        User user = User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();
        Notification.builder()
                .content("hello")
                .isRead(false)
                .type(NotificationType.INVITE)
                .id(1)
                .user(user)
                .build();
    }

    @DisplayName("알림을 저장할 수 있다.")
    @Test
    void test2() {
        User user = User.builder()
                .id(1)
                .name("이재훈")
                .phoneNumber("010-5538-6818")
                .build();
        userRepository.save(user);
        Notification notification = Notification.builder()
                .content("hello")
                .isRead(false)
                .type(NotificationType.INVITE)
                .id(1)
                .user(user)
                .build();
        notificationRepository.save(notification);
    }
}