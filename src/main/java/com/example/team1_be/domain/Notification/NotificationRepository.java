package com.example.team1_be.domain.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.team1_be.domain.User.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findAllByUser(User user);
}
