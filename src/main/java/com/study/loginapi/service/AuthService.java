package com.study.loginapi.service;

import com.study.loginapi.entity.Auth;
import com.study.loginapi.repository.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;

    public Auth findAuthByMemberId(Long memberId) {
        return authMapper.findOneByMemberId(memberId);
    }

    public int saveAuth(String accessToken, String refreshToken, Long memberId) {
        return authMapper.saveAuth(Auth.builder(accessToken, refreshToken, memberId)
                .build());
    }

    public int updateAuth(Long authId, String accessToken, String refreshToken, Long memberId) {
        return authMapper.updateAuth(
                Auth.builder(accessToken, refreshToken, memberId)
                        .authId(authId)
                        .build());
    }
}
