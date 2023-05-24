package com.example.sidepot.work.event;

import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.RejectMessage;
import com.example.sidepot.notification.work.domain.Sender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AcceptedCoverCanceledEvent {

    private CoverManagerId coverManagerId;

    private Sender sender;
    private RejectMessage rejectMessage;

    public AcceptedCoverCanceledEvent(CoverManagerId coverManagerId, Sender sender, RejectMessage rejectMessage) {
        this.coverManagerId = coverManagerId;
        this.sender = sender;
        this.rejectMessage = rejectMessage;
    }
}
