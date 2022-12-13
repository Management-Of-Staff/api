package com.example.sidepot.global.config.security;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.BaseEntity;
import com.example.sidepot.member.domain.BaseEntityRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtFilter<T extends BaseEntity> extends BasicAuthenticationFilter {

    private final TokenProvider tokenProvider;
    private final BaseEntityRepository baseEntityRepository;

    public JwtFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider, BaseEntityRepository baseEntityRepository) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.baseEntityRepository = baseEntityRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String reqHeader = request.getHeader("Authorization");

        if(reqHeader == null || !reqHeader.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }
        String token = reqHeader.substring("Bearer ".length());
        Claims claims = tokenProvider.validAndGetUserPhone(token);

        String userId = claims.getSubject();

        Optional<T> base = baseEntityRepository.findByPhone(userId);
        T t = base.orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(t.getPhone(), null, t.getAuthorities())
        );

        chain.doFilter(request, response);
    }
}
