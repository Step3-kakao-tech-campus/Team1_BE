package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private HttpStatus httpStatus;
	private ClientErrorCode errorCode;

	public CustomException(HttpStatus httpStatus, ClientErrorCode errorCode) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

	public ApiUtils.ApiResult<?> body() { return ApiUtils.error(errorCode); }

	public HttpStatus status() {
		return httpStatus;
	}
}
