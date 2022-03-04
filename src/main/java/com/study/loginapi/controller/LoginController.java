package com.study.loginapi.controller;

import com.study.loginapi.dto.SignUpRequestDto;
import com.study.loginapi.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/member")
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        loginService.signUp(signUpRequestDto);
    }
}
