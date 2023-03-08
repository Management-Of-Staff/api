package com.example.sidepot.work.domain.refactor.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Access(value = AccessType.FIELD)
@Embeddable
public class RealWorkTime {
    @Column(name = "realStartTime")
    private LocalDateTime realStartTime;
    @Column(name = "realEndTime")
    private LocalDateTime realEndTime;
    @Column(name = "realWorkingTime")
    private Long realWorkingTime;

    protected RealWorkTime(){
    }
    public RealWorkTime(LocalDateTime realStartTime, LocalDateTime realEndTime, Long realWorkingTime) {
        this.realStartTime = realStartTime;
        this.realEndTime = realEndTime;
        this.realWorkingTime = realWorkingTime;
    }
}
