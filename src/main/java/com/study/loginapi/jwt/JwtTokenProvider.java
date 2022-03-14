package com.study.loginapi.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    private Key secretKey;
    private Key refreshKey;

    private final long ACCESS_TOKEN_VALID_TIME = 1 * 60 * 1000L;   // 1분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주

    @PostConstruct
    protected void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        refreshKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        log.info("secretKey: " + secretKey);
        log.info("refreshKey: " + refreshKey);
    }

    public String createAccessToken(String memberId) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("memberId", memberId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME)) // set Expire Time
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String memberId) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId); //
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(refreshKey)
                .compact();
    }

    public boolean isValidRefreshToken(String refreshToken) {
        try {
            Claims accessClaims = getClaimsToken(refreshToken);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("userId"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get("userId"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    private Claims getClaimsToken(String refreshToken) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(String.valueOf(refreshKey)))
                .parseClaimsJws(refreshToken)
                .getBody();
    }
}
