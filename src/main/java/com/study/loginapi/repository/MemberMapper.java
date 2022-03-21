package com.study.loginapi.repository;

import com.study.loginapi.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member findOneById(String memberId);

    int getCountById(String memberId);

    void saveOne(Member member);
}
