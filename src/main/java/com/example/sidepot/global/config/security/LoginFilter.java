package com.example.sidepot.global.config.security;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.BaseEntity;
import com.example.sidepot.member.domain.BaseEntityRepository;
import com.example.sidepot.member.dto.MemberDto.ReqMemberLoginDto.LoginType;
import com.example.sidepot.member.dto.MemberDto.ReqMemberLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final BaseEntityRepository baseEntityRepository;
    private final TokenProvider tokenProvider;
    private final ObjectMapper mapper;
    public LoginFilter(AuthenticationManager authenticationManager, BaseEntityRepository baseEntityRepository, TokenProvider tokenProvider, ObjectMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.baseEntityRepository = baseEntityRepository;
        this.tokenProvider = tokenProvider;
        this.mapper = mapper;
        setFilterProcessesUrl("/rest/v1/owners/login");
        setFilterProcessesUrl("/rest/v1/staff/login");
    }



    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ReqMemberLoginDto member = mapper.readValue(request.getInputStream(), ReqMemberLoginDto.class);

        if(member.getLoginType().equals(LoginType.FORM)){
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(
                    member.getPhone(), member.getPassword());

          return authenticationManager.authenticate(authenticationToken);

        } else if(member.getLoginType().equals(LoginType.REFRESH)){

            if(StringUtils.isEmpty(member.getLoginType())) {throw new Exception(ErrorCode.NOT_FOUND_REFRESH_TOKEN);}

            if(tokenProvider.validateToken(member.getRefreshToken())){
               BaseEntity baseEntity = (BaseEntity) baseEntityRepository.findByPhone(member.getPhone()).get();
               return new UsernamePasswordAuthenticationToken(
                               baseEntity.getPhone(),null, baseEntity.getAuthorities());

            } else{
               throw new Exception(ErrorCode.REFRESH_TOKEN_EXPIRE);
            }

        } else{
            throw new IllegalArgumentException("타입이 올바르지 않음");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetails user = (UserDetails) authResult.getPrincipal();

        response.addHeader(tokenProvider.AUTH_HEADER,
                TokenProvider.BEARER + tokenProvider.generateToken(user.getUsername(), TokenProvider.TokenType.ACCESS));

        response.addHeader(tokenProvider.REFRESH_HEADER,
                TokenProvider.BEARER + tokenProvider.generateToken(user.getUsername(), TokenProvider.TokenType.REFRESH));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("error", failed.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
