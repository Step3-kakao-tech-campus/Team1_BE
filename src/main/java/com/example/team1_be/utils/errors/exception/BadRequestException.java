package com.example.team1_be.utils.errors.exception;

import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
	}

	public HttpStatus status() {
		return HttpStatus.BAD_REQUEST;
	}
}