package com.example.team1_be.domain.Group.Invite;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(indexes = @Index(name = "groupInviteCode", columnList = "code", unique = true))
public class Invite extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String code;

	private LocalDateTime renewedAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	@NotNull
	private Group group;

	@Builder
	public Invite(Long id, String code, LocalDateTime renewedAt, Group group) {
		this.id = id;
		this.code = code;
		this.renewedAt = renewedAt;
		this.group = group;
	}

	public void renew() {
		this.renewedAt = LocalDateTime.now();
	}
}
