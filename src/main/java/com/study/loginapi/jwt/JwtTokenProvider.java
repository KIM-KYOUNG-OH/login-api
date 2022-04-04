package com.study.loginapi.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    private Key secretKey;

    final long ACCESS_TOKEN_VALID_TIME = 60 * 1000L;   // 1분
    final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주

    @PostConstruct
    protected void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("secretKey: " + secretKey);
    }

    public String createAccessToken(String memberId) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId); //
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String memberId) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId); //
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(secretKey)
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            log.info(claims.getHeader().toString());
            log.info(claims.getBody().toString());
            log.info(claims.getSignature());
        } catch (JwtException e) {
            log.info("Token is invalid!");
            return false;
        } catch (NullPointerException e) {
            log.info("Token is absent!");
            return false;
        }

        return true;
    }

    public Long getMemberId(String refreshToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken);
        return (Long) claims.getBody().get("memberId");
    }
}
