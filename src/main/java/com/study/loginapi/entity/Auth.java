package com.study.loginapi.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("Auth")
@Getter
@Builder
public class Auth {
    private Long authId;
    private String accessToken;
    private String refreshToken;
    private Long memberId;

    public static AuthBuilder builder(String accessToken, String refreshToken, Long memberId) {
        return new AuthBuilder().accessToken(accessToken).refreshToken(refreshToken).memberId(memberId);
    }
}
