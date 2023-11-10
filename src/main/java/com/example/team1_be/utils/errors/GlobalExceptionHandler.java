package com.example.team1_be.utils.errors;

import com.example.team1_be.utils.errors.exception.NotUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.exception.CustomException;
<<<<<<< HEAD

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
=======
import com.example.team1_be.utils.errors.exception.ForbiddenException;
import com.example.team1_be.utils.errors.exception.NotFoundException;
import com.example.team1_be.utils.errors.exception.ServerErrorException;
import com.example.team1_be.utils.errors.exception.UnauthorizedException;

import lombok.RequiredArgsConstructor;
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalExceptionHandler {

<<<<<<< HEAD
	// log 관련
	private ResponseEntity<?> handleException(Exception e, String message, HttpStatus status) {
		log.error(message, e);
		ApiUtils.ApiResult<?> error = ApiUtils.error(message, ClientErrorCode.UNKNOWN_ERROR);
		return new ResponseEntity<>(error, status);
	}

	// kakao - 404 회원이 아닙니다
	@ExceptionHandler(NotUserException.class)
	public ResponseEntity<?> notUserException(NotUserException exception) {
		return new ResponseEntity<>(exception.body(), exception.status());
	}

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
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(NoHandlerFoundException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.INVALID_URI.getMessage(), ClientErrorCode.INVALID_URI);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// -10003
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handlePathVariableException(MethodArgumentTypeMismatchException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.INVALID_PARAMETER.getMessage(), ClientErrorCode.INVALID_PARAMETER);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
=======
	private ResponseEntity<?> handleException(Exception e, String message, HttpStatus status) {
		log.error(message, e);
		ApiUtils.ApiResult<?> error = ApiUtils.error(message, status);
		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequest(BadRequestException e) {
		return handleException(e, "잘못된 요청입니다.", e.status());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> unauthorized(UnauthorizedException e) {
		return handleException(e, "인증되지 않은 요청입니다.", e.status());
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<?> forbidden(ForbiddenException e) {
		return handleException(e, "금지된 요청입니다.", e.status());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<?> notFound(NotFoundException e) {
		return handleException(e, "찾을 수 없는 리소스입니다.", e.status());
	}

	@ExceptionHandler(ServerErrorException.class)
	public ResponseEntity<?> serverError(ServerErrorException e) {
		return handleException(e, "서버 내부 오류입니다.", e.status());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownException(Exception e) {
		return handleException(e, "알 수 없는 오류로 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
	}

	// -10004
	@ExceptionHandler(MethodArgumentNotValidException.class)
<<<<<<< HEAD
	public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException exception) {
		ApiUtils.ApiResult<?> error = ApiUtils.error(exception.getAllErrors().get(0).getDefaultMessage(), ClientErrorCode.INVALID_REQUEST_BODY);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// -10005
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		exception.printStackTrace();
		ApiUtils.ApiResult<?> error = ApiUtils.error("요청값의 양식이 잘못되었습니다.", ClientErrorCode.INVALID_FORM_INPUT);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// -10002
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> unknownException(Exception exception) {
		exception.printStackTrace();
		ApiUtils.ApiResult<?> error = ApiUtils.error(ClientErrorCode.UNKNOWN_ERROR.getMessage(), ClientErrorCode.UNKNOWN_ERROR);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
=======
	public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException e) {
		return handleException(e, "유효하지 않은 요청 파라미터입니다.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		return handleException(e, e.getMessage(), e.status());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handlePathVariableException(MethodArgumentTypeMismatchException e) {
		return handleException(e, "요청주소의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return handleException(e, "요청값의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
	}
}