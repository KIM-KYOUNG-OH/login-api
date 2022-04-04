package com.study.loginapi.exception;

public class CannotGenerateIdException extends RuntimeException {

    public CannotGenerateIdException(String message) {
        super(message);
    }
}
