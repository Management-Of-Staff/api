package com.example.sidepot;

import com.example.sidepot.security.SidePotProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({SidePotProperties.class})
@SpringBootApplication
public class SidepotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SidepotApplication.class, args);
    }





}
