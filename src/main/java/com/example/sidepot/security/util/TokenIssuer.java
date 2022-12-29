package com.example.sidepot.security.util;

import com.example.sidepot.security.SidePotProperties;
import com.example.sidepot.security.domain.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class TokenIssuer {

    public static final String PLATFORM = "O_JIC";

    public static final String KEY_ROLE = "roles";

    private SecretKey key;

    private SecretKey rKey;

    private final SidePotProperties sidePotProperties;


    public TokenIssuer(SidePotProperties sidePotProperties) {
        this.sidePotProperties = sidePotProperties;
        this.key = Keys.hmacShaKeyFor(
                sidePotProperties.getAccessKey().getBytes(StandardCharsets.UTF_8));
        this.rKey = Keys.hmacShaKeyFor(
                sidePotProperties.getRefreshKey().getBytes(StandardCharsets.UTF_8));
    }

    public Claims parseAccessClaims(String token){
        return parseClaims(token, TokenType.ACCESS);
    }

    public Claims parseRefreshClaims(String token){
        return  parseClaims(token, TokenType.REFRESH);
    }

    public SecretKey sign(TokenType type){
        switch (type){
            case REFRESH:
                return rKey;
            case ACCESS:
            default:
                return key;
        }
    }


    private Claims parseClaims(String token, TokenType type){
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(sign(type)).build().parseClaimsJws(token).getBody();
        } catch (SignatureException signatureException) {
            log.info("적합한 키가 아닙니다.");
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("만료된 토큰입니다.");
        } catch (MalformedJwtException malformedJwtException) {
            log.info("위조된 토큰입니다.");
        } catch (IllegalArgumentException illegalArgumentException) {
            log.info("적합한 키가 아닙니다.");
        }
        return claims;
    }

    public String createAccessToken(Auth auth, String authority){
        return createToken(auth, authority, TokenType.ACCESS, key);
    }

    public String createRefreshToken(Auth auth, String authority){
        return createToken(auth, authority, TokenType.REFRESH, rKey);
    }

    public String createToken(Auth auth, String authority ,TokenType type, SecretKey key){

        return Jwts.builder()
                .claim("userId", auth.getAuthId())
                .claim("name", auth.getName())
                .claim(KEY_ROLE, Collections.singleton(authority))
                .signWith(key) /* signWith(sing(type)) */
                .setSubject(auth.getPhone())
                .setIssuer(PLATFORM)
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
}
