package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private ClientErrorCode errorCode;
	private HttpStatus httpStatus;

	public CustomException(String message, HttpStatus httpStatus) {
		super(message);
		this.errorCode = ClientErrorCode.UNKNOWN_ERROR;
		this.httpStatus = httpStatus;
	}

	public CustomException(ClientErrorCode errorCode, HttpStatus httpStatus) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public ApiUtils.ApiResult<?> body() { return ApiUtils.error(getMessage(), errorCode); }

	public HttpStatus status() {
		return httpStatus;
	}
}