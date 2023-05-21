package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SenderId {

    private Long senderId;

    private String senderName;

    public SenderId(Long senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SenderId senderId1 = (SenderId) o;
        return Objects.equals(senderId, senderId1.senderId) && Objects.equals(senderName, senderId1.senderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, senderName);
    }
}
