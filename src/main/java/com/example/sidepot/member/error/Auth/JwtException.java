package com.example.sidepot.member.error.Auth;

import com.example.sidepot.member.error.BaseErrorCode;
import com.example.sidepot.member.error.BaseException;

public class JwtException extends BaseException {

    private BaseErrorCode baseErrorCode;

    public JwtException(BaseErrorCode baseErrorCode){
        this.baseErrorCode = baseErrorCode;
    }

    @Override
    public BaseErrorCode getErrorCode() {
        return baseErrorCode;
    }
}
