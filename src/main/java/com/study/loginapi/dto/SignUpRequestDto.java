package com.study.loginapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String memberId;
    private String name;
    private String password;
    private String email;
}
