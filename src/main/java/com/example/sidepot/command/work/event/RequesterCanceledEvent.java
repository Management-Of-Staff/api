package com.example.sidepot.command.work.event;

import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.domain.Sender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequesterCanceledEvent {
    private CoverManager coverManager;
    private Sender sender;
    private Receiver receiver;

    public RequesterCanceledEvent(CoverManager coverManager, Sender sender, Receiver receiver) {
        this.coverManager = coverManager;
        this.sender = sender;
        this.receiver = receiver;
    }
}
