package com.example.sidepot.global.config;

import com.example.sidepot.global.config.security.JwtFilter;
import com.example.sidepot.global.config.security.LoginFilter;
import com.example.sidepot.global.config.security.TokenProvider;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.domain.BaseEntityRepository;
import com.example.sidepot.member.util.PasswordEncoderConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebSecurity
public class StaffSecurityConfig extends WebSecurityConfigurerAdapter {

    private final StaffService staffService;
    private final PasswordEncoderConfig passwordEncoder;
    private final TokenProvider tokenProvider;
    private final BaseEntityRepository baseEntityRepository;

    private final ObjectMapper objectMapper;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), baseEntityRepository, tokenProvider, objectMapper);
        JwtFilter jwtFilter = new JwtFilter(authenticationManager(), tokenProvider, baseEntityRepository);
        http
                .csrf().disable()
                .addFilter(loginFilter)// swagger API 호출시 403 에러 발생 방지 // 토큰 방식도 안 씀
                .addFilter(jwtFilter)
                //TODO Matchers 오너 직원 다 수정해야함 - API 나오고
                .requestMatchers()
                .antMatchers("/rest/v1/staff/**").and()
                .authorizeRequests()
                .antMatchers("/rest/v1/staff/register").permitAll()
                .antMatchers("/rest/v1/staff/login").authenticated() // 로그인을 위로 올리고 여기는 다른 기능
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(staffService).passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
    }
}
