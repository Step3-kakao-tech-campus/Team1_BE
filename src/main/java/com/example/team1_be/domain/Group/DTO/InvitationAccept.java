package com.example.team1_be.domain.Group.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class InvitationAccept {
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		@NotBlank(message = "초대키가 누락되었습니다.")
		private String invitationKey;
	}
}
