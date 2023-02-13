package com.example.sidepot.global.security;

import com.example.sidepot.member.domain.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final String KEY_ROLES = "roles";
    private final TokenIssuer issuer;

    public JwtAuthenticationProvider(TokenIssuer issuer) {
        this.issuer = issuer;
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Claims claims) {
        String role = (String) claims.get(KEY_ROLES);
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, JwtException {
        Claims claims = issuer.parseAccessClaims(((JwtAuthenticationToken) authentication).getToken());
        return new JwtAuthenticationToken(new Auth(claims) ,"",grantedAuthorities(claims));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
