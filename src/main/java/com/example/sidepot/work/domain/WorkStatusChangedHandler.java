package com.example.sidepot.work.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WorkStatusChangedHandler {

    @EventListener(WorkStatusChangedHandler.class)
    public void handle(WorkStatusChangedEvent event){}
}
