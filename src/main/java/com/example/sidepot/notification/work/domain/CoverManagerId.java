package com.example.sidepot.notification.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CoverManagerId {
    private Long coverManagerId;

    public CoverManagerId(Long coverManagerId) {
        this.coverManagerId = coverManagerId;
    }
}
