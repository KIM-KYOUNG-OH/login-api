package com.study.loginapi.global;

import com.study.loginapi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedNicknameException.class)
    protected ErrorResponse handleDuplicatedNicknameException(DuplicatedNicknameException e) {
        log.error("DuplicatedIdException", e);
        return ErrorResponse.of(ErrorCode.DUPLICATED_NICKNAME_ERROR);
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    protected ErrorResponse handleDuplicatedEmailException(DuplicatedEmailException e) {
        log.error("DuplicatedEmailException", e);
        return ErrorResponse.of(ErrorCode.DUPLICATED_EMAIL_ERROR);
    }

    @ExceptionHandler(AbsentMemberException.class)
    protected ErrorResponse handleAbsentMemberException(AbsentMemberException e) {
        log.error("AbsentMemberException", e);
        return ErrorResponse.of(ErrorCode.ABSENT_MEMBER_ERROR);
    }

    @ExceptionHandler(AbsentTokenException.class)
    protected ErrorResponse handleAbsentTokenException(AbsentTokenException e) {
        log.error("AbsentTokenException", e);
        return ErrorResponse.of(ErrorCode.ABSENT_TOKEN_ERROR);
    }

    @ExceptionHandler(WrongPasswordException.class)
    protected ErrorResponse handleWrongPasswordException(WrongPasswordException e) {
        log.error("WrongPasswordException", e);
        return ErrorResponse.of(ErrorCode.WRONG_PASSWORD_ERROR);
    }

    // 개발자가 직접 핸들링해서 다른 예외로 던지지 않으면 모두 이곳으로 모인다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
