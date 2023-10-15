package com.example.team1_be.utils.errors;

import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }


    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownException(Exception exception) {
        ApiUtils.ApiResult<?> error = ApiUtils.error("알 수 없는 오류로 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException exception) throws JsonProcessingException {
        ApiUtils.ApiResult<?> error = ApiUtils.error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        ApiUtils.ApiResult<?> error = ApiUtils.error(e.getMessage(), e.status());
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handlePathVariableException(MethodArgumentTypeMismatchException e) {
        ApiUtils.ApiResult<?> error = ApiUtils.error("요청값의 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(error);
    }
}