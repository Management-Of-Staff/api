package com.example.sidepot.command.notification.work.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NoticeType {

    REQUESTED("님이 요청을 했어요!"),
    ACCEPTED("님이 수락을 했어요!");

    private String message;
}
