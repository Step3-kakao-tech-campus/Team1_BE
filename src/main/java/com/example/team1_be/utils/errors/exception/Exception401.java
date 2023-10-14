package com.example.team1_be.utils.errors.exception;

import com.example.team1_be.utils.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}