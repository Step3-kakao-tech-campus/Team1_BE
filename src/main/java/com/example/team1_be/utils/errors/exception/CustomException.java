package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiUtils.ApiResult<?> body(){return ApiUtils.error(getMessage(), status());}

    public HttpStatus status(){return httpStatus;};
}
