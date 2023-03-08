package com.example.sidepot.work.domain.refactor.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class DailyWork {

    @Embedded
    private StoreId storeId;


    @Enumerated(EnumType.STRING)
    private Attendance attendance;
    @Embedded
    private RealWorkTime realWorkTime;

    protected DailyWork() {
    }

    public DailyWork(StoreId storeId, Attendance attendance, RealWorkTime realWorkTime) {
        this.storeId = storeId;
        this.attendance = attendance;
        this.realWorkTime = realWorkTime;
    }

    public StoreId getEmploymentId() {
        return storeId;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public RealWorkTime getRealWorkTime() {
        return realWorkTime;
    }
}
