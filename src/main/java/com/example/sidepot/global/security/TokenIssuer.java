package com.example.sidepot.global.security;


import com.example.sidepot.member.domain.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

    public Claims parseAccessClaims(String token) throws JwtException {
        return parseClaims(token);
    }

    public Claims parseRefreshClaims(String token) throws JwtException{
        return  parseClaims(token);
    }

    private Claims parseClaims(String token) throws JwtException {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String createAccessToken(Auth auth){return createToken(auth, TokenType.ACCESS);}

    public String createRefreshToken(Auth auth){
        return createToken(auth, TokenType.REFRESH);
    }

    public Date setExpiryTime(TokenType tokenType){
        switch (tokenType){
            case REFRESH:
                return Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
            case ACCESS:
            default:
                return Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));
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
