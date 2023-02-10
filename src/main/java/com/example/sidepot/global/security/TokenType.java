package com.example.sidepot.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS("AccessToken", 60 * 180L),
    REFRESH("RefreshToken", 60 * 60 * 24L);

    private final String description;
    private final Long expiration;
}