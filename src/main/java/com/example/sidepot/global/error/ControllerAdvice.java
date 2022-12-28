package com.example.sidepot.global.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({BindException.class})
    public ResponseEntity handleValidEx(BindException exception){
        log.error("@ValidException 발생!", exception.getMessage());
        return new ResponseEntity(new ExceptionDto( HttpStatus.BAD_REQUEST.value(), "@ValidException 발생! {}"),
                HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    static class ExceptionDto {
        private Integer errorCode;
        private String errorMessage;
    }
    @Data
    @AllArgsConstructor
    static class ExceptionsDto {
        private Integer errorCode;
        private List<String> errorMessage;
    }
}
