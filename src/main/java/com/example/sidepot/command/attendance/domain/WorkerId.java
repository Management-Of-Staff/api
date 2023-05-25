package com.example.sidepot.command.attendance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class WorkerId {

    private Long workerId;
    private String workerName;

    public WorkerId(Long workerId, String workerName) {
        this.workerId = workerId;
        this.workerName = workerName;
    }
}
