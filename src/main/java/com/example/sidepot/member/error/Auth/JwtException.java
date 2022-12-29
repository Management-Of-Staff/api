package com.example.sidepot.global.error.Auth;

import com.example.sidepot.global.error.BaseErrorCode;
import com.example.sidepot.global.error.BaseException;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import io.jsonwebtoken.Jwt;

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
