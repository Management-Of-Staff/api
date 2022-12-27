package com.example.sidepot.security.authentication;

import com.example.sidepot.security.error.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final String KEY_ROLES = "roles";

    private SecretKey key;

    public JwtAuthenticationProvider(@Value("${side-pot.sign-key}") String accessKey) {
        this.key = Keys.hmacShaKeyFor(
                accessKey.getBytes(StandardCharsets.UTF_8));
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Claims claims) {
        List<String> roles = (List) claims.get(KEY_ROLES);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (String role : roles) { grantedAuthorities.add(() -> role); }

        return grantedAuthorities;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(((JwtAuthenticationToken) authentication).getToken()).getBody();
        } catch (SignatureException signatureException) {
            throw new TokenException("시크릿키가 아닙니다.", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new TokenException("만료된 토큰입니다.", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new TokenException("위조된 토큰입니다.", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new TokenException("올바르지 않은 인자를 포함했습니다.", illegalArgumentException);
        }
        return new JwtAuthenticationToken(grantedAuthorities(claims),claims.getSubject(),"");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
