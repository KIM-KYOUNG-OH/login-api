package com.study.loginapi.exception;

public class DuplicatedNicknameException extends RuntimeException{
    public DuplicatedNicknameException() {
    }

    public DuplicatedNicknameException(String message) {
        super(message);
    }
}
