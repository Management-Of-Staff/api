package com.example.sidepot.global.error;


import com.example.sidepot.global.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { Exception.class})
    protected ResponseDto handleCustomException(Exception e){
        return ResponseDto.builder()
                .path("")
                .method("")
                .statusCode(e.getErrorCode().getHttpValue())
                .message(e.getErrorCode().getErrorMessage())
                .data("")
                .build();
    }
}
