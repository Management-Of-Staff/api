package com.example.sidepot.security.util;


import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.security.SidePotProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class TokenIssuer {

    public static final String PLATFORM = "O_JIK";
    public static final String KEY_ROLE = "roles";

    private final SecretKey key;

    private final SidePotProperties sidePotProperties;

    public TokenIssuer(SidePotProperties sidePotProperties) {
        this.sidePotProperties = sidePotProperties;
        this.key = Keys.hmacShaKeyFor(
                sidePotProperties.getAccessKey().getBytes(StandardCharsets.UTF_8));
    }

    public Claims parseAccessClaims(String token){
        return parseClaims(token);
    }

    public Claims parseRefreshClaims(String token){
        return  parseClaims(token);
    }

    private Claims parseClaims(String token) throws RuntimeException {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SignatureException | IllegalArgumentException signatureException) {
            log.info("올바르지 않은 토큰 입니다.");
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("토큰이 만료되었습니다.");
        }
        log.info("[토큰만료 시간] : " + claims.getExpiration());
        return claims;
    }

    public String createAccessToken(Auth auth){return createToken(auth, TokenType.ACCESS);}

    public String createRefreshToken(Auth auth){
        return createToken(auth, TokenType.REFRESH);
    }

    public Date setExpiryTime(TokenType tokenType){
        switch (tokenType){
            case REFRESH:
                return Date.from(Instant.now().plus(360, ChronoUnit.MINUTES));
            case ACCESS:
            default:
                return Date.from(Instant.now().plus(180, ChronoUnit.MINUTES));
        }
    }

    public String createToken(Auth auth, TokenType tokenType){

        return Jwts.builder()
                .claim("userId", auth.getAuthId())
                .claim("name", auth.getName())
                .claim("phone", auth.getPhone())
                .claim(KEY_ROLE, auth.getRole())
                .signWith(key)
                .setSubject(auth.getPhone())
                .setIssuer(PLATFORM)
                .setIssuedAt(new Date())
                .setExpiration(setExpiryTime(tokenType))
                .compact();
    }
}
