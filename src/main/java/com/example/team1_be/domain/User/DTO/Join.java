package com.example.team1_be.domain.User.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Join {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {
		@NotBlank(message = "code가 누락되었습니다.")
		private String code;
		@NotBlank(message = "userName이 누락되었습니다.")
		private String userName;

		private Boolean isAdmin;
	}

	@Getter
	public static class Response {
		private final Boolean isAdmin;

		public Response(Boolean isAdmin) {
			this.isAdmin = isAdmin;
		}

	}
}
