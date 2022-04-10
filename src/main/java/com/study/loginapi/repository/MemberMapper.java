package com.study.loginapi.repository;

import com.study.loginapi.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    Optional<Member> findOneById(String memberId);

    Optional<Member> findOneByEmail(String email);

    boolean findByNickname(String nickname);

    int getCountById(String memberId);

    void saveOne(Member member);

    boolean findByEmail(String email);
}
