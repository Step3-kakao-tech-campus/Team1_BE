package com.example.team1_be.domain.Notification;

import java.util.List;
import java.util.stream.Collectors;

import com.example.team1_be.domain.Group.DTO.ExistNonRead;
import org.springframework.stereotype.Service;

import com.example.team1_be.domain.Notification.DTO.NotificationInfo;
import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public void saveNotification(Notification notification) {
		notificationRepository.save(notification);
	}

	public NotificationInfo.Response findAllNotification(User user) {
		// 알림 조회
		List<Notification> notifications = notificationRepository.findAllByUser(user);

		// 알림 읽음 처리
		notifications.stream().forEach(notification -> notification.setIsRead(true));

		// notification to notice convert
		List<NotificationInfo.Response.Notice> notices = notifications.stream().map(notification ->
			NotificationInfo.Response.Notice.builder()
				.content(notification.getContent())
				.notificationType(notification.getType().toString())
				.date(notification.getCreatedAt().toString()).build()
		).collect(Collectors.toList());

		// response body 생성
		NotificationInfo.Response notificationInfo = new NotificationInfo.Response(notices);

		return notificationInfo;
	}

	public ExistNonRead existNonReadNotification(User user) {
		// 알림 조회
		List<Notification> notifications = notificationRepository.findAllByUser(user);

		// 읽지 않은 알림 여부에 따른 객체 반환
		return new ExistNonRead(notifications.stream().anyMatch(notification -> !notification.getIsRead()));
	}
}