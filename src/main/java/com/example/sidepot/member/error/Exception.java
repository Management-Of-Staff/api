package com.example.sidepot.member.error;


public class Exception extends RuntimeException{

    private ErrorCode errorCode;
    public Exception(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}

