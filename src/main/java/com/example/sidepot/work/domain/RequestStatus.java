package com.example.sidepot.work.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestStatus {

    WAITING("수락 대기 중"),
    ACCEPTED("수락 처리"),
    REJECTED("거절");

    private String value;
}
