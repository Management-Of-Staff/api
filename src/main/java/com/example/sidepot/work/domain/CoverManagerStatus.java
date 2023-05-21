package com.example.sidepot.work.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CoverManagerStatus {

    WAITING,
    EXPIRE,
    ACCEPTED,
    CANCEL,

}
