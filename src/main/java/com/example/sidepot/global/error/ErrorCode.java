package com.example.sidepot.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // HttpStatus.BAD_REQUEST
    BAD_REQUEST_KOSCOM_API_SERVER(HttpStatus.BAD_REQUEST.value(), "코스콤 서버 요청에 실패했어요"),
    BAD_REQUEST_STOCK_CATEGORY_DELETE(HttpStatus.BAD_REQUEST.value(), "삭제하고자 하는 카테고리를 사용하고 있는 영역이 존재해요"),
    BAD_REQUEST_STOCK_MEMBER_MAP_CREATE(HttpStatus.BAD_REQUEST.value(), "즐겨찾기 등록에 실패했어요"),
    ALREADY_HAS_ROLE(HttpStatus.BAD_REQUEST.value(), "이미 권한을 가지고 있습니다."),
    PHONE_DUPLICATE(HttpStatus.BAD_REQUEST.value(), "이미 사용 중인 핸드폰 입니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "회원 가입되지 않은 사용자입니다."),
    MEMBER_WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "잘못된 비밀번호 입니다."),
    REFRESH_TOKEN_EXPIRE(HttpStatus.BAD_REQUEST.value(), "(로그인타입이 올바르지 않음) 다시 로그인 해주세요 -> 로그인페이지"),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.BAD_REQUEST.value(), "리프레쉬 토큰이 없음"),
    INVALID_LOGIN_TYPE(HttpStatus.BAD_REQUEST.value(), "올바르지 못한 로그인 입니다 -> 유형(폼), 유형(리프레쉬)"),
    ALREADY_DELETED_MEMBER(HttpStatus.BAD_REQUEST.value(), "탈퇴한 회원입니다."),
    FAILED_UPDATE_PASSWORD(HttpStatus.BAD_REQUEST.value(), "패스워드 변경에 실패했어요"),
    FAILED_UPDATE_PROFILE(HttpStatus.BAD_REQUEST.value(), "프로필 변경에 실패했어요"),
    FAILED_UPDATE_PHONE(HttpStatus.BAD_REQUEST.value(), "핸드폰 번호 변경에 실패했어요"),

    // HttpStatus.UNAUTHORIZED
    MAIL_ADDRESS_PARSING_FAIL(HttpStatus.UNAUTHORIZED.value(), "잘못된 메일 주소입니다"),
    UNAUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다."),


    ;

    private int httpValue;
    private String ErrorMessage;
    ErrorCode(int httpValue, String ErrorMessge) {
        this.httpValue = httpValue;
        this.ErrorMessage = ErrorMessge;
    }
}
