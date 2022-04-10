package com.study.loginapi.repository;

import com.study.loginapi.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    Auth findOneByMemberId(Long memberId);

    void saveAuth(Auth auth);

    void updateAuth(Auth auth);
}
