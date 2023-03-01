package com.example.sidepot.global.config;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.security.CustomAccessDeniedHandler;
import com.example.sidepot.global.security.CustomAuthenticationEntryPoint;
import com.example.sidepot.global.security.JwtAuthenticationProvider;
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
            "/swagger-ui.html/**",
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
            Path.REST_BASE_PATH + "/staffs/register"
    };

    public WebSecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder,
                             JwtAuthenticationProvider jwtAuthenticationProvider) throws Exception {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain permitFilter(HttpSecurity http) throws Exception {
        http
                .requestMatchers(requestMatchers ->
                        requestMatchers
                                .mvcMatchers(PERMIT_URL_ARRAY)
                                .mvcMatchers(PERMIT_URL_AUTH_ARRAY)
                )
                .authorizeRequests(authorize ->
                        authorize.anyRequest().permitAll()
                )
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();
        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception{
        log.debug("WebSecurityConfig 필터 중..");
        http
                .csrf().disable()
                .cors().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                    .antMatchers(Path.REST_BASE_PATH + "/auth/**").authenticated()
                    .antMatchers(Path.REST_BASE_PATH + "/owners/**").hasAnyAuthority(ROLE_OWNER, ROLE_ADMIN)
                    .antMatchers(Path.REST_BASE_PATH + "/staffs/**").hasAnyAuthority(ROLE_STAFF, ROLE_ADMIN)
                    .antMatchers(Path.REST_BASE_PATH + "/work/**").hasAnyAuthority(ROLE_OWNER, ROLE_ADMIN)
                    .antMatchers(Path.REST_BASE_PATH + "/stores/**").hasAnyAuthority(ROLE_OWNER, ROLE_ADMIN)
                .anyRequest().permitAll()
                .and()
                .headers()
                    .frameOptions().disable()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .apply(new JwtSecurityConfig(authenticationManagerBuilder.getOrBuild()));

        return http.build();
    }
}

