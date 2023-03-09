package com.example.sidepot.work.domain.refactor.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Access(value = AccessType.FIELD)
@Embeddable
public class RealWorkTime {
    @Column(name = "real_start_time")
    private LocalDateTime realStartTime;

    @Column(name = "real_end_time")
    private LocalDateTime realEndTime;

    @Column(name = "real_working_time")
    private Long realWorkingTime;

    protected RealWorkTime(){
    }
    public RealWorkTime(LocalDateTime realStartTime, LocalDateTime realEndTime, Long realWorkingTime) {
        this.realStartTime = realStartTime;
        this.realEndTime = realEndTime;
        this.realWorkingTime = realWorkingTime;
    }

    public LocalDateTime getRealStartTime() {
        return realStartTime;
    }

    public LocalDateTime getRealEndTime() {
        return realEndTime;
    }

    public Long getRealWorkingTime() {
        return realWorkingTime;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
