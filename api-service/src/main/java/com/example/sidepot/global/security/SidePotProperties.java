package com.example.sidepot.global.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "side-pot")
public class SidePotProperties {

    private String accessKey;
    private String refreshKey;

}
