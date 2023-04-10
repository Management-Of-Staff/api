package com.example.sidepot.global.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfig {
    private ApplicationContext applicationContext;

    public EventsConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Bean
    public InitializingBean eventsInitializer(){
        return () -> Events.setPublisher(applicationContext);
    }
}
