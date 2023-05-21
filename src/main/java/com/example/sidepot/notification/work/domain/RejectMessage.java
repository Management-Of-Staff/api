package com.example.sidepot.notification.work.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RejectMessage {

    CANT_DO_IT("해낼 자신이 없어요"),
    HASTY("섣부른 생각이였던 것 같아요"),
    ANOTHER_SCHEDULE("다른 일정이 생겼어요"),
    ETC("기타");


    private String value;
}
