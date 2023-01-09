package com.example.sidepot.global.error;


import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
public class Exception extends RuntimeException{

    private final ErrorCode errorCode;
    public Exception(ErrorCode errorCode){
        this.errorCode = errorCode;
    }


}

