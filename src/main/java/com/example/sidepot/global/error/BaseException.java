package com.example.sidepot.global.error;

public abstract class BaseException extends RuntimeException{
    public abstract BaseErrorCode getErrorCode();
}
