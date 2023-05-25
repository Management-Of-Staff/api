package com.example.sidepot.command.work.event;

import com.example.sidepot.command.notification.work.domain.Sender;
import com.example.sidepot.command.notification.work.domain.CoverManagerId;
import com.example.sidepot.command.notification.work.domain.RejectMessage;
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
