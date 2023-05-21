package com.example.sidepot.notification.work.domain;

import com.example.sidepot.work.domain.SenderId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CoverManagerId {
    private Long coverManagerId;
    private SenderId senderId;
    public CoverManagerId(Long coverManagerId, SenderId senderId) {
        this.coverManagerId = coverManagerId;
        this.senderId = senderId;
    }
}
