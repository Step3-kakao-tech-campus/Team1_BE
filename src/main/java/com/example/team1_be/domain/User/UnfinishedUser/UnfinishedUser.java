package com.example.team1_be.domain.User.UnfinishedUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class UnfinishedUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String code;

	@NotNull
	private Long kakaoId;

	@Builder
	public UnfinishedUser(Long id, String code, Long kakaoId) {
		this.id = id;
		this.code = code;
		this.kakaoId = kakaoId;
	}
}
