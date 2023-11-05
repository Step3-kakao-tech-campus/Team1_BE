package com.example.team1_be.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.exception.BadRequestException;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.ForbiddenException;
import com.example.team1_be.utils.errors.exception.NotFoundException;
import com.example.team1_be.utils.errors.exception.ServerErrorException;
import com.example.team1_be.utils.errors.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequest(BadRequestException e) {
		return new ResponseEntity<>(e.body(), e.status());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> unauthorized(UnauthorizedException e) {
		return new ResponseEntity<>(e.body(), e.status());
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> forbidden(ForbiddenException e) {
		return new ResponseEntity<>(e.body(), e.status());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException e) {
		return new ResponseEntity<>(e.body(), e.status());
	}

	@ExceptionHandler(ServerErrorException.class)
	public ResponseEntity<?> serverError(ServerErrorException e) {
		return new ResponseEntity<>(e.body(), e.status());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownException(Exception exception) {
		System.out.println(exception.getMessage());
		exception.printStackTrace();
		ApiUtils.ApiResult<?> error = ApiUtils.error("알 수 없는 오류로 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException exception) throws
		JsonProcessingException {
		ApiUtils.ApiResult<?> error = ApiUtils.error(
			exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(e.getMessage(), e.status());
		return new ResponseEntity<>(error, e.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handlePathVariableException(MethodArgumentTypeMismatchException e) {
		ApiUtils.ApiResult<?> error = ApiUtils.error("요청주소의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		ApiUtils.ApiResult<?> error = ApiUtils.error("요청값의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}