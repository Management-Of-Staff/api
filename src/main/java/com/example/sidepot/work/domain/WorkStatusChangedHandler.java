package com.example.sidepot.work.domain;

import com.example.sidepot.work.app.AttendanceService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorkStatusChangedHandler {
    private final AttendanceService attendanceService;

    @EventListener(WorkStatusChangedHandler.class)
    public void handle(WorkStatusChangedEvent event){
        attendanceService.changeWorkStatus();
    }
}
