package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String message) {
		super(message);
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(getMessage(), ClientErrorCode.UNKNOWN_ERROR);
	}

	public HttpStatus status() {
		return HttpStatus.FORBIDDEN;
	}
}