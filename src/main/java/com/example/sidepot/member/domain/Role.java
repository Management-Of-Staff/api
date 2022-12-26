package com.example.sidepot.member.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"), STAFF("ROLE_STAFF"), OWNER("ROLE_OWNER");

    private String authorities;

    Role(String authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getAuthority() {
        return this.authorities;
    }

}
