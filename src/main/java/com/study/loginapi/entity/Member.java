package com.study.loginapi.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Alias("Member")
@Getter
@Builder
public class Member {

    private Long memberId;
    private String nickname;
    private String password;
    private String email;

    public static MemberBuilder builder(String nickname, String password) {
        return new MemberBuilder().nickname(nickname).password(password);
    }
}
