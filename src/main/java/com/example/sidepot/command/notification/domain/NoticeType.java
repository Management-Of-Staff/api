package com.example.sidepot.command.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoticeType {

    REQUESTED("님이 요청을 했어요!", "없어"),
    ACCEPTED("님이 수락을 했어요!", "없어"),


    STAFF_COVER_REQUESTED("대타 요청", "대타 알림함으로"),
    STAFF_COVER_ACCEPTED("대타 수락", "대타 알림함으로"),

    R_STAFF_COVER_CANCEL("요창자가 대타 취소", "대타 수락 알림 탭으로"),
    A_STAFF_COVER_CANCEL("수락자가 대타 취소", "대타 요창 알림 탭으로"),
    OWNER_WORK_CHANGE("스케쥴 변동", "스케쥴 변동 알림함으로"),

    ALL_REJECTED("모두 거절되었습니다.", "대타 알림함URL"),

    CHECK_IN("출근하기", "출/퇴근하기로"),
    CHECK_OUT("퇴근하기", "출/퇴근하기로");

    private String message;
    private String relatedUrl;
}
