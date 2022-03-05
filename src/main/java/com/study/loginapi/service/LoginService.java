package com.study.loginapi.service;

import com.study.loginapi.dto.SignInRequestDto;
import com.study.loginapi.dto.SignUpRequestDto;

public interface LoginService {

    void signUp(SignUpRequestDto signUpRequestDto);

    String login(SignInRequestDto signInRequestDto);
}
