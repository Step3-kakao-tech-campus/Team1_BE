package com.example.team1_be.utils.errors;

import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.Exception400;
import com.example.team1_be.utils.errors.exception.Exception404;
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

@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalExceptionHandler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownException(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiUtils.ApiResult<?> error = ApiUtils.error("알 수 없는 오류로 실패했습니다.", status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleRequestDTOValidationException(MethodArgumentNotValidException exception) throws JsonProcessingException {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiUtils.ApiResult<?> error = ApiUtils.error(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.status()).body(e.body());
    }
}
