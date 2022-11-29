package com.example.sidepot.global.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
                .csrf().disable() // swagger API 호출시 403 에러 발생 방지
                .authorizeRequests()
                    .antMatchers(PERMIT_URL_ARRAY).permitAll()
                    .anyRequest().permitAll();
        http
                .headers().frameOptions().disable();
    }
}