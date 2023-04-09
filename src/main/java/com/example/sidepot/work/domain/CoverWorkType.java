package com.example.sidepot.work.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CoverWorkType {
    REQUEST("요청자"), ACCEPT("수락자");

    private String description;
}
