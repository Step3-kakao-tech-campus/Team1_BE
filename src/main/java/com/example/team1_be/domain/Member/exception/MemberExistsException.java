package com.example.team1_be.domain.Member.exception;

public class MemberExistsException extends RuntimeException {
    public MemberExistsException() {
    }

    public MemberExistsException(String message) {
        super(message);
    }

    public MemberExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberExistsException(Throwable cause) {
        super(cause);
    }

    public MemberExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
