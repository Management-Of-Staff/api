package com.example.sidepot.member.error.Auth;


import com.example.sidepot.member.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum JwtErrorCode implements BaseErrorCode {

    EXPIRE_TOKEN( HttpStatus.BAD_REQUEST.value(), "만료된 토큰입니다.");

    private int httpValue;
    private String errorMessage;

    JwtErrorCode(int httpValue, String errorMessage) {

        this.httpValue=httpValue;
        this.errorMessage = errorMessage;
    }

    @Override
    public int httpValue() {
        return this.httpValue;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
