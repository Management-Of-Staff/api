package com.example.sidepot.command.attendance.event;

import com.example.sidepot.command.attendance.domain.WorkerId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CheckedInEvent {

    private WorkerId workerId;
    private LocalDateTime now;

    public CheckedInEvent(WorkerId workerId, LocalDateTime now) {
        this.workerId = workerId;
        this.now = now;
    }
}
