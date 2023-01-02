package com.example.sidepot.global.error;


public class Exception extends RuntimeException{

    private ErrorCode errorCode;
    public Exception(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}

