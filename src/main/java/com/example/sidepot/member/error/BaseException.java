package com.example.sidepot.member.error;

public abstract class BaseException extends RuntimeException{
    public abstract BaseErrorCode getErrorCode();
}
