package com.example.sidepot.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter @AllArgsConstructor
public enum TokenType {
    ACCESS("AccessToken", Date.from(Instant.now().plus(59, ChronoUnit.MINUTES))),
    REFRESH("RefreshToken", Date.from(Instant.now().plus(59, ChronoUnit.MINUTES)));
    
    String description;
    Date expiryTime;
}
