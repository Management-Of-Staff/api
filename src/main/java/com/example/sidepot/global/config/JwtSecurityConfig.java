package com.example.sidepot.global.config;


import com.example.sidepot.global.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationManager authenticationManager;

    @Override
    public void configure(HttpSecurity http) {
        log.info("JwtSecurityConfig 필터 실행 중...");
        JwtFilter filter = new JwtFilter(authenticationManager);
        http.addFilterAfter(filter, LogoutFilter.class);
    }
}