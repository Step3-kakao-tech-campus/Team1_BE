package com.example.team1_be.domain.Group.DTO;

import javax.validation.constraints.NotBlank;

import com.example.team1_be.domain.Group.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Create {
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Request {
		@NotBlank(message = "매장이름이 누락되었습니다.")
		private String marketName;
		@NotBlank(message = "매장번호가 누락되었습니다.")
		private String marketNumber;
		@NotBlank(message = "도로명주소가 누락되었습니다.")
		private String mainAddress;
		@NotBlank(message = "건물번호가 누락되었습니다.")
		private String detailAddress;

		public Group toGroup() {
			return Group.builder()
				.name(this.marketName)
				.address(this.mainAddress + this.detailAddress)
				.businessNumber(this.marketName)
				.build();
		}
	}
}
