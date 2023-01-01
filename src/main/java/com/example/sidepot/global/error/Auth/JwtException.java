package com.example.sidepot.global.error.Auth;

import com.example.sidepot.global.error.BaseErrorCode;
import com.example.sidepot.global.error.BaseException;

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
