package com.example.sidepot.global.error;


import com.example.sidepot.global.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    protected ResponseDto handleCustomException(Exception e){
        return ResponseDto.builder()
                .path("")
                .method("")
                .statusCode(e.getErrorCode().getHttpValue())
                .message(e.getErrorCode().getErrorMessage())
                .data("")
                .build();
    }
    /**
     * 모든 최상위 자바 Exception 테스트 로그 용도
     */
    @ExceptionHandler(value = {java.lang.Exception.class})
    protected ResponseDto handleNativeException(java.lang.Exception e){
        return  ResponseDto.builder()
                .message(String.valueOf(e.getCause()))
                .data(e.getMessage())
                .build();
    }
}