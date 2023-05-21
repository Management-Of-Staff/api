package com.example.sidepot.work.event;

import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.RejectMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AcceptedCoverCanceledEvent {

    private CoverManagerId coverManagerId;
    private RejectMessage rejectMessage;

    public AcceptedCoverCanceledEvent(CoverManagerId coverManagerId, RejectMessage rejectMessage) {
        this.coverManagerId = coverManagerId;
        this.rejectMessage = rejectMessage;
    }
}
