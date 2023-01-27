package com.example.sidepot.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingStatus {

    WORKING("근무 중"), LATENESS("지각"), ABSENCE("부재 중");

    private String status;
}
