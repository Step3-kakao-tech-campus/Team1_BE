package com.example.team1_be.domain.Notification;

import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult> findAllNotification(@AuthenticationPrincipal CustomUserDetails userDetails) {
        NotificationResponse notificationResponse = notificationService.findAllNotification(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(notificationResponse));
    }
}