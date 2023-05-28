package com.example.sidepot.command.notification.work.domain;

import com.example.sidepot.command.member.domain.Staff;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Receiver {

    private Long receiverId;
    private String receiverName;
    private String receiverToken;

    public Receiver(Long receiverId, String receiverToken) {
        this.receiverId = receiverId;
        this.receiverToken = receiverToken;
    }

    public Receiver(Long receiverId, String receiverName, String receiverToken) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverToken = receiverToken;
    }

    public Receiver(Staff staff) {
        this.receiverId = staff.getMemberId();
        this.receiverName = staff.getMemberName();
        this.receiverToken = staff.getUuid();
    }

}
