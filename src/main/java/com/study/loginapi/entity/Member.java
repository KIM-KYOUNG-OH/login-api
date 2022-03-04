package com.study.loginapi.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Alias("Member")
@Getter
@Builder(builderMethodName = "memberBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    private String memberId;
    private String name;
    private String password;
    private String email;

    public static MemberBuilder builder(String memberId) {
        if(memberId == null) throw new IllegalArgumentException("memberId is required");
        return memberBuilder().memberId(memberId);
    }
}
