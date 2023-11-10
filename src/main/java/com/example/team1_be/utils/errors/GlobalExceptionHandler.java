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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalExceptionHandler {

	private ResponseEntity<?> handleException(Exception e, String message, HttpStatus status,
		ClientErrorCode errorCode) {
		log.error(message, e);
		ApiUtils.ApiResult<?> error = ApiUtils.error(message, errorCode);
		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequest(BadRequestException e) {
		return handleException(e, e.getMessage(), e.status(), e.getErrorCode());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> unauthorized(UnauthorizedException e) {
		return handleException(e, e.getMessage(), e.status(), e.getErrorCode());
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> forbidden(ForbiddenException e) {
		return handleException(e, e.getMessage(), e.status(), e.getErrorCode());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException e) {
		return handleException(e, e.getMessage(), e.status(), e.getErrorCode());
	}

	@ExceptionHandler(ServerErrorException.class)
	public ResponseEntity<?> serverError(ServerErrorException e) {
		return handleException(e, e.getMessage(), e.status(), e.getErrorCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownException(Exception e) {
		return handleException(e, "알 수 없는 오류로 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR,
			ClientErrorCode.UNKNOWN_ERROR);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		return handleException(e, e.getMessage(), e.status(), ClientErrorCode.UNKNOWN_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException e) {
		return handleException(e, "유효하지 않은 요청 파라미터입니다.", HttpStatus.BAD_REQUEST, ClientErrorCode.INVALID_REQUEST_BODY);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handlePathVariableException(MethodArgumentTypeMismatchException e) {
		return handleException(e, "요청주소의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST, ClientErrorCode.INVALID_PARAMETER);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return handleException(e, "요청값의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST, ClientErrorCode.INVALID_FORM_INPUT);
	}
}