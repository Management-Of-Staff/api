package com.example.sidepot.command.work.event;

import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.domain.Sender;
import com.example.sidepot.command.work.domain.RejectMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AcceptorCanceledEvent {
    private CoverManager coverManager;
    private Sender sender;
    private Receiver receiver;
    private RejectMessage rejectMessage;

    public AcceptorCanceledEvent(CoverManager coverManager, Sender sender, Receiver receiver, RejectMessage rejectMessage) {
        this.coverManager = coverManager;
        this.sender = sender;
        this.receiver = receiver;
        this.rejectMessage = rejectMessage;
    }
}
