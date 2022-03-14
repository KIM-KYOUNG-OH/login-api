package com.study.loginapi.repository;

import com.study.loginapi.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    void save(Auth auth);

    Optional<Auth> findOneByMemberId(Long memberId);

    Integer updateByAuthId(Long authId, String refreshToken);
}
