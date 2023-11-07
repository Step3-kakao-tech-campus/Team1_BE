package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.errors.ClientErrorCode;
import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
	private ClientErrorCode errorCode;

	public UnauthorizedException(ClientErrorCode errorCode) { this.errorCode = errorCode; }

	public ApiUtils.ApiResult<?> body() { return ApiUtils.error(errorCode); }

	public HttpStatus status() {
		return HttpStatus.UNAUTHORIZED;
	}
}