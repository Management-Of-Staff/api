package com.example.sidepot.global.security;


import com.example.sidepot.command.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public class TokenIssuer {

    public static final String KEY_ROLE = "roles";


    private final SecretKey key;

    private final SidePotProperties sidePotProperties;
    private final MemberFactory memberFactory;

    public TokenIssuer(SidePotProperties sidePotProperties, MemberFactory memberFactory) {
        this.sidePotProperties = sidePotProperties;
        this.memberFactory = memberFactory;
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

    public String createAccessToken(Member member){
        return Jwts.builder()
                .claim("memberId", member.getMemberId())
                .claim("name", member.getMemberName())
                .claim("phoneNum", member.getMemberPhoneNum())
                .claim(KEY_ROLE, member.getRole())
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(6000, ChronoUnit.MINUTES)))
                .compact();
    }

    public String createRefreshToken(){
        return Jwts.builder()
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .compact();
    }
}
