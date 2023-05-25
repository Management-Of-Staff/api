package com.example.sidepot.command.work.event;

import com.example.sidepot.command.notification.work.domain.CoverManagerId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestedCoverCanceledEvent {

    private CoverManagerId coverManagerId;

    public RequestedCoverCanceledEvent(CoverManagerId coverManagerId) {
        this.coverManagerId = coverManagerId;
    }
}
