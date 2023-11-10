package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {
	public ServerErrorException(String message) {
		super(message);
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(getMessage(), ClientErrorCode.UNKNOWN_ERROR);
	}

	public HttpStatus status() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
