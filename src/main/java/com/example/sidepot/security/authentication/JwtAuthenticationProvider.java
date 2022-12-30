package com.example.sidepot.security.authentication;

import com.example.sidepot.security.app.AuthService;

import com.example.sidepot.security.domain.Auth;
import com.example.sidepot.security.domain.AuthRepository;

import com.example.sidepot.security.util.TokenIssuer;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final String KEY_ROLES = "roles";

    private final TokenIssuer issuer;

    private final AuthService service;

    private final AuthRepository authRepository;

    private final AuthService authService;

    public JwtAuthenticationProvider(TokenIssuer issuer, AuthService service, AuthRepository authRepository, AuthService authService) {
        this.issuer = issuer;
        this.service = service;
        this.authRepository = authRepository;
        this.authService = authService;
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Claims claims) {
        List<String> roles = (List) claims.get(KEY_ROLES);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (String role : roles) { grantedAuthorities.add(() -> role); }

        return grantedAuthorities;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims = issuer.parseAccessClaims(((JwtAuthenticationToken) authentication).getToken());

        return new JwtAuthenticationToken(new Auth(claims) ,"",grantedAuthorities(claims));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
