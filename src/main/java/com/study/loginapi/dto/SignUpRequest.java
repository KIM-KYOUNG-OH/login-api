package com.study.loginapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String nickname;
    private String password;
    private String email;
}
