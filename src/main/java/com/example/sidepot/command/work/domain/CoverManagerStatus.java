package com.example.sidepot.command.work.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoverManagerStatus {
    WAITING("수락 대기 중인 상태"),
    EXPIRE("수락되지 않은 상태에서 요청이 만료된 상태"),
    ACCEPTED("수락된 상태"),
    CANCEL("취소된 상태");
    private String value;
}
