package com.example.sidepot.security.config;

import com.example.sidepot.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final AuthenticationManager authenticationManager;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter filter = new JwtFilter(authenticationManager);
        http.addFilterAfter(filter, LogoutFilter.class);
    }
}