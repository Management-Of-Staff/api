package com.example.sidepot.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Configuration
@ConfigurationProperties(prefix = "side-pot")
public class SidePotProperties {

    private String accessKey = "zsdioufgasdoifyuq3jklrhzdkjlvhuiotyvo7q8yrojoisedhsakjlvnzcx5fds23dafa8j";

    private String refreshKey = "zxcvlkjdsfiopufas8301d3cnsddcn3h9cnu0qvasdfsdagdfgdfshbbvccsdzl2";

    private Date expiryAccess = Date.from(
            Instant.now()
                    .plus(59, ChronoUnit.MINUTES)
    );

    private Date expiryRefresh= Date.from(
            Instant.now()
                    .plus(1,ChronoUnit.DAYS)
    );

}
