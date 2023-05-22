package com.example.sidepot.attendance.domain;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    CHECK_IN("출석"),
    CHECK_OUT("정상 퇴근"),
    LATE("지각"),
    EARLY_LEAVE("조퇴"),
    ABSENCE("결근"),

    INITIAL("초기 등록");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public boolean isCheckOut() {
        return CHECK_OUT.equals(this);
    }
}
