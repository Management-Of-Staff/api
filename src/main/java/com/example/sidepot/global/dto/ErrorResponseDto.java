package com.example.sidepot.global.dto;

import com.example.sidepot.global.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;

    public static ResponseEntity<ErrorResponseDto> toResponseDto(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpValue())
                .body(ErrorResponseDto.builder()
                        .status(errorCode.getHttpValue())
                        .message(errorCode.getErrorMessage())
                        .build()
                );
    }
}
