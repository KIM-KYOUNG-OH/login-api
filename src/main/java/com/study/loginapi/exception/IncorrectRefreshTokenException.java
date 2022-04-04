package com.study.loginapi.exception;

public class IncorrectRefreshTokenException extends RuntimeException {
    public IncorrectRefreshTokenException(String message) {
        super(message);
    }
}
