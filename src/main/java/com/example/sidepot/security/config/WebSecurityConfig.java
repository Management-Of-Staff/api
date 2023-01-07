package com.example.sidepot.security.config;

import com.example.sidepot.global.Path;
import com.example.sidepot.security.CustomAccessDeniedHandler;
import com.example.sidepot.security.CustomAuthenticationEntryPoint;
import com.example.sidepot.security.authentication.JwtAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final String ROLE_STAFF = "STAFF";
    private final String ROLE_OWNER = "OWNER";
    private final String ROLE_ADMIN = "ADMIN";

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    private static final String[] PERMIT_URL_AUTH_ARRAY = {
            /* 리프레시 토큰 */
            Path.REST_BASE_PATH + "/auth/login",
            Path.REST_BASE_PATH + "/auth/reissue",

            /* 회원가입 */
            Path.REST_BASE_PATH + "/owners/register",
            Path.REST_BASE_PATH + "/staffs/register",
    };

    public WebSecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder,
                             JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("WebSecurityConfig 필터 중..");
        http
                .csrf()
                    .disable()
                .cors()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .authorizeRequests()
                    .antMatchers(PERMIT_URL_ARRAY).permitAll()
                    .antMatchers(PERMIT_URL_AUTH_ARRAY).permitAll()
                    .antMatchers(Path.REST_BASE_PATH + "/auth/**").authenticated()
                    .antMatchers(Path.REST_BASE_PATH + "/owners/**").hasAnyRole(ROLE_OWNER, ROLE_ADMIN)
                    .antMatchers(Path.REST_BASE_PATH + "/staffs/**").hasAnyRole(ROLE_STAFF, ROLE_ADMIN)
                    .antMatchers(Path.REST_BASE_PATH + "/work/**").hasAnyRole(ROLE_OWNER, ROLE_ADMIN)
                .anyRequest().permitAll()
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .apply(new JwtSecurityConfig(authenticationManagerBuilder.getOrBuild()))
                ;

        return http.build();
    }
}

