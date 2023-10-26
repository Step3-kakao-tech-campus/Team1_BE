package com.example.team1_be.domain.Notification;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team1_be.domain.Notification.DTO.NotificationInfo;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@GetMapping
	public ResponseEntity<ApiUtils.ApiResult> findAllNotification(
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		NotificationInfo.Response notificationInfo = notificationService.findAllNotification(userDetails.getUser());
		return ResponseEntity.ok(ApiUtils.success(notificationInfo));
	}
}