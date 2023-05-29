package com.example.sidepot.command.work.event;

import com.example.sidepot.command.work.domain.CoverManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RequestedCoverCanceledEvent {

    private CoverManager coverManager;

    public RequestedCoverCanceledEvent(CoverManager coverManager) {
        this.coverManager = coverManager;
    }
}
