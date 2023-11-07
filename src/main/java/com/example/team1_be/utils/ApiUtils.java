package com.example.team1_be.utils;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ApiUtils {
	public static <T> ApiResult<T> success(T response) {
		return new ApiResult<>(true, response, null);
	}

	public static ApiResult<?> error(ClientErrorCode errorCode) {
		return new ApiResult<>(false, null, new ApiError(errorCode.getMessage(), errorCode.getValue()));
	}

	@Getter
	@AllArgsConstructor
	public static class ApiResult<T> {
		private final boolean success;
		private final T response;
		private final ApiError error;
	}

	@Getter
	@AllArgsConstructor
	public static class ApiError {
		private final String message;
		private final int errorCode;
	}
}
