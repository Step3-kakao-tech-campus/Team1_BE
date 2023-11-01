package com.example.team1_be.domain.Notification;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
	@Setter
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
