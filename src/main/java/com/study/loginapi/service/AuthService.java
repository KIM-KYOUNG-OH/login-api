package com.study.loginapi.service;

import com.study.loginapi.entity.Auth;
import com.study.loginapi.repository.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;

    public Optional<Auth> findAuthByMemberId(Long memberId) {
        return authMapper.findOneByMemberId(memberId);
    }

    public Integer updateRefreshToken(Long authId, String refreshToken) {
        return authMapper.updateByAuthId(authId, refreshToken);
    }
}
