package com.example.sidepot.global.dto;


import java.time.LocalDateTime;

public interface DescriptiveResponse {
    String getPath();

    String getMethod();

    String getMessage();

    Object getData();

    int getStatusCode();

    LocalDateTime getTimestamp();
}
