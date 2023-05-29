package com.example.sidepot.command.work.event;

import com.example.sidepot.command.work.domain.CoverManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoverNoticeAllRejectedEvent {

    private CoverManager coverManager;

    public CoverNoticeAllRejectedEvent(CoverManager coverManager) {
        this.coverManager = coverManager;

    }
}
