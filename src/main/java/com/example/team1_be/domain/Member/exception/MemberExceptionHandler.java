package com.example.team1_be.domain.Member.exception;

import com.example.team1_be.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {
    @ExceptionHandler(MemberExistsException.class)
    public ResponseEntity<ApiUtils.ApiResult> handleMemberExistsException(MemberExistsException e) {
        return new ResponseEntity<>(ApiUtils.error("이미 그룹이 존재하는 멤버입니다.", HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiUtils.ApiResult> handleMemberNotFoundException(MemberNotFoundException e) {
        return new ResponseEntity<>(ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}