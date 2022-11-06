package com.example.sidepot.global.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode{

    // HttpStatus.BAD_REQUEST
    BAD_REQUEST_KOSCOM_API_SERVER(HttpStatus.BAD_REQUEST.value(), "코스콤 서버 요청에 실패했어요"),
    BAD_REQUEST_STOCK_CATEGORY_DELETE(HttpStatus.BAD_REQUEST.value(), "삭제하고자 하는 카테고리를 사용하고 있는 영역이 존재해요"),
    BAD_REQUEST_STOCK_MEMBER_MAP_CREATE(HttpStatus.BAD_REQUEST.value(), "즐겨찾기 등록에 실패했어요"),
    ALREADY_HAS_ROLE(HttpStatus.BAD_REQUEST.value(), "이미 권한을 가지고 있습니다."),

    // HttpStatus.UNAUTHORIZED
    MAIL_ADDRESS_PARSING_FAIL(HttpStatus.UNAUTHORIZED.value(), "잘못된 메일 주소입니다"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다."),
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 이메일입니다."),

    ;

    private int value;
    private String msg;
    ErrorCode(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }


}
