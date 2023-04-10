package com.example.sidepot.global.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
    private static ApplicationEventPublisher applicationEventPublisher;

    static void setPublisher(ApplicationEventPublisher applicationEventPublisher){
        Events.applicationEventPublisher = applicationEventPublisher;
    }

    public static void raise(Object event){
        if(applicationEventPublisher != null){
            applicationEventPublisher.publishEvent(event);
        }
    }
}
