package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {
	private ClientErrorCode errorCode;

	public ServerErrorException(String message) {
		super(message);
		this.errorCode = ClientErrorCode.UNKNOWN_ERROR;
	}

	public ServerErrorException(String message, ClientErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(getMessage(), errorCode);
	}

	public HttpStatus status() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
