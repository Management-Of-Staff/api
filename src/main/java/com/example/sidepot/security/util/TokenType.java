package com.example.sidepot.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum TokenType {
    ACCESS("AccessToken"),
    REFRESH("RefreshToken");

    String description;
}
