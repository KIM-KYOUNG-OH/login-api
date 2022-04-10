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

    public void saveAuth(String refreshToken, Long memberId) {
        authMapper.saveAuth(Auth.builder(refreshToken, memberId)
                .build());
    }

    public void updateAuth(Long authId, String refreshToken, Long memberId) {
        authMapper.updateAuth(
                Auth.builder(refreshToken, memberId)
                        .authId(authId)
                        .build());
    }
}
