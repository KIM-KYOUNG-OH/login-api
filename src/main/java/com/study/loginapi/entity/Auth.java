package com.study.loginapi.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("Auth")
@Getter
@Builder
public class Auth {
    private Long authId;
    private String refreshToken;
    private Long memberId;

    public static AuthBuilder builder(String refreshToken, Long memberId) {
        return new AuthBuilder().refreshToken(refreshToken).memberId(memberId);
    }
}
