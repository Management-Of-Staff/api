package com.example.sidepot.notification.work.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class ReceiverId {

    private Long receiverId;
    private String receiverToken;

    public ReceiverId(Long receiverId, String receiverToken) {
        this.receiverId = receiverId;
        this.receiverToken = receiverToken;
    }
}
