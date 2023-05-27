package com.example.sidepot.command.notification.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Sender {

    private Long senderId;
    private String senderName;
    //private String senderToken;

    public Sender(Long senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sender sender1 = (Sender) o;
        return Objects.equals(senderId, sender1.senderId) && Objects.equals(senderName, sender1.senderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, senderName);
    }
}
