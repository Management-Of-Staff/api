package com.example.sidepot.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private Object principal;
    private Object credentials;

    public JwtAuthenticationToken(String token){
        super(null);
        this.token = token;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                  Object principal,
                                  Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getToken(){
        return this.token;
    }
}
