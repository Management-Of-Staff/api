package com.example.sidepot.security;

import com.example.sidepot.global.error.Exception;
import com.example.sidepot.security.authentication.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER = "Authentication";

    public static final Integer BEARER_LENGTH = 7;

    public static final String BEARER = "Bearer ";

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = parseBearerToken(request);

        if(token != null && !token.equalsIgnoreCase("null")){
            try {
                Authentication tokenAuthentication = new JwtAuthenticationToken(token);
                Authentication authentication = authenticationManager.authenticate(tokenAuthentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e){

                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request) throws ServletException, IOException {
        String bearerToken =  request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)){
            return bearerToken.substring(BEARER_LENGTH);
        }
        return null;
    }
}
