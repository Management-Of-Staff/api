package com.example.sidepot.global.config.security;

import com.example.sidepot.member.dto.MemberDto.ReqMemberLoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {

    public static final String AUTH_HEADER = "Authentication";
    public static final String REFRESH_HEADER = "refresh-token";
    public static final String BEARER = "Bearer ";
    private SecretKey key;
    SidePotProperties sidePotProperties;

    public enum TokenType{
        ACCESS,
        REFRESH
    }

    public TokenProvider(SidePotProperties sidePotProperties) {
        this.sidePotProperties = sidePotProperties;
        this.key = Keys.hmacShaKeyFor(
                sidePotProperties.getSignKey().getBytes(StandardCharsets.UTF_8));
    }

    public String singleToken(String user){
        return generateToken(user, TokenType.ACCESS);
    }
    public String generateToken(String user, TokenType type){
        return Jwts.builder()
                .signWith(key)
                .setSubject(user)
                .setIssuer("O_JIC")
                .setIssuedAt(new Date())
                .setExpiration(expiryTime(type))
                .compact();
    }

    public Date expiryTime(TokenType type){
        switch(type){
            case REFRESH:
                return this.sidePotProperties.getExpiryRefresh();
            case ACCESS:
            default:
                return this.sidePotProperties.getExpiryAccess();
        }
    }

    public Claims validAndGetUserPhone(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public boolean validateToken(String jwt){
        return this.getClaims(jwt) != null;
    }

    private Claims getClaims(String jwt){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
