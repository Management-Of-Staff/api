package com.example.sidepot.global.config;

import com.example.sidepot.global.config.security.JwtFilter;
import com.example.sidepot.global.config.security.LoginFilter;
import com.example.sidepot.global.config.security.TokenProvider;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.domain.BaseEntityRepository;
import com.example.sidepot.member.util.PasswordEncoderConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class OwnerSecurityConfig extends WebSecurityConfigurerAdapter {

    private final OwnerService ownerService;
    private final PasswordEncoderConfig passwordEncoder;
    private final TokenProvider tokenProvider;
    private final BaseEntityRepository baseEntityRepository;

    private final ObjectMapper objectMapper;
    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            /* h2-console*/
            "/h2-console/**"
    };

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .addFilter(new LoginFilter(authenticationManagerBean(), baseEntityRepository, tokenProvider, objectMapper))// swagger API 호출시 403 에러 발생 방지 // 토큰 방식도 안 씀
                    .addFilter(new JwtFilter(authenticationManagerBean(), tokenProvider, baseEntityRepository))
                    .authorizeRequests()
                    .antMatchers("/rest/v1/owners/login").hasRole("OWNER") //로그인을 아래로 내리고 다른 기능 추가
                    .antMatchers("/rest/v1/owners/register").permitAll()
                    .antMatchers(PERMIT_URL_ARRAY).permitAll()
                    .anyRequest().hasRole("OWNER")
                    .and()
                    .headers().frameOptions().disable()
                    .and()
                    .formLogin()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    ;
        }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(ownerService).passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
        }


}

