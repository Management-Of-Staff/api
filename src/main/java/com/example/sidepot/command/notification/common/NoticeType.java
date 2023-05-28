package com.example.sidepot.command.notification.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoticeType {

    REQUESTED("님이 요청을 했어요!", "없어"),
    ACCEPTED("님이 수락을 했어요!", "없어"),


    COVER_REQUESTED("대타 요청", "대타 알림함으로"),
    COVER_ACCEPTED("대타 수락", "대타 알림함으로"),

    CHECK_IN("출근하기", "출/퇴근하기로"),
    CHECK_OUT("퇴근하기", "출/퇴근하기로");

    private String message;
    private String relatedUrl;
}
