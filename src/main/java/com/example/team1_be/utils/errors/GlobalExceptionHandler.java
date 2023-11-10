package com.example.team1_be.utils.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(exception.getMessage(), exception.getErrorCode());
		return new ResponseEntity<>(error, exception.getHttpStatus());
	}

	// -10000
	@ExceptionHandler(TimeoutException.class)
	public ResponseEntity<?> timeoutException(TimeoutException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.TIMEOUT.getMessage(), ClientErrorCode.TIMEOUT);
		return new ResponseEntity<>(error, HttpStatus.REQUEST_TIMEOUT);
	}

	// -10002
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException exception) {
//		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.INVALID_URI.getMessage(), ClientErrorCode.INVALID_URI);
//		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//	}

	// -10003
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handlePathVariableException(MethodArgumentTypeMismatchException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.INVALID_PARAMETER.getMessage(), ClientErrorCode.INVALID_PARAMETER);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// -10004
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(exception.getAllErrors().get(0).getDefaultMessage(), ClientErrorCode.INVALID_REQUEST_BODY);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// ?
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		ApiUtils.ApiResult<?> error = ApiUtils.error("요청값의 양식이 잘못되었습니다.", ClientErrorCode.UNKNOWN_ERROR);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// -10002
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownException(Exception exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.UNKNOWN_ERROR.getMessage(), ClientErrorCode.UNKNOWN_ERROR);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}