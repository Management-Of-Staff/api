package com.example.sidepot.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {

    MANAGER("매니저"),
    PULL_TIME_EMPLOYEE("정규직"),
    PART_TIME_WORKER("계약직"),
    DAILY_WORKER("일용직"),
    ETC("기타")
    ;
    private String value;
}
