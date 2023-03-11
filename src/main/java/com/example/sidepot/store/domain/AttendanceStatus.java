package com.example.sidepot.store.domain;

import lombok.Getter;

@Getter
public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    EARLY_LEAVE("조퇴"),
    ABSENCE("결근"),
    NORMAL_DEPARTURE("정상 퇴근");

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }
}
