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
        log.info("요청헤더: "+ reqHeader);
        //헤더에 토큰이 없으면 다음 필터
        if(reqHeader == null || !reqHeader.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }
        //토큰 받아와서 검증
        String token = reqHeader.substring("Bearer ".length());
        log.info("토큰: "+ token);
        Claims claims = tokenProvider.validAndGetUserPhone(token);
        //검증된 유저라면
        if(claims.getSubject() != null){
            String userId = claims.getSubject();
            log.info("유저 아이디(폰): " + userId);
            Optional<T> base = baseEntityRepository.findByPhone(userId);
            T t = base.orElseThrow(()->new Exception(ErrorCode.MEMBER_NOT_FOUND));

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(t.getPhone(), null, t.getAuthorities())
            );
        } else{
            throw new IllegalArgumentException("이상한 토큰??");
        }

        chain.doFilter(request, response);
    }
}
