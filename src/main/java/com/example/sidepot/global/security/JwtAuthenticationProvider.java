package com.example.sidepot.global.security;

import com.example.sidepot.member.domain.Member;
import com.example.sidepot.member.domain.Role;
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

    private final TokenIssuer issuer;
    private final MemberFactory memberFactory;

    public JwtAuthenticationProvider(TokenIssuer issuer, MemberFactory memberFactory) {
        this.issuer = issuer;
        this.memberFactory = memberFactory;
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Role role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, JwtException {
        Claims claims = issuer.parseAccessClaims(((JwtAuthenticationToken) authentication).getToken());
        Member member = memberFactory.loadMemberByJwtClaims(claims);
        return new JwtAuthenticationToken(
                new LoginMember(
                        member.getMemberId(),
                        member.getMemberPhoneNum(),
                        member.getMemberName()),
                " ",
                grantedAuthorities(member.getRole()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
