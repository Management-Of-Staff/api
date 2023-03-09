package com.example.sidepot.work.domain.refactor.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
@Embeddable
public class DailyWork {
    @Embedded
    private RealWorkTime realWorkTime;
    protected DailyWork() {
    }

    public DailyWork(StoreId storeId, RealWorkTime realWorkTime) {
        this.realWorkTime = realWorkTime;
    }

    public RealWorkTime getRealWorkTime() {
        return realWorkTime;
    }
}
