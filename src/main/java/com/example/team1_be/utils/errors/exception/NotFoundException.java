package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
	private ClientErrorCode errorCode;

	public NotFoundException(String message) {
		super(message);
		this.errorCode = ClientErrorCode.UNKNOWN_ERROR;
	}

	public NotFoundException(ClientErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ApiUtils.ApiResult<?> body() { return ApiUtils.error(getMessage(), errorCode); }

	public HttpStatus status() {
		return HttpStatus.NOT_FOUND;
	}
}