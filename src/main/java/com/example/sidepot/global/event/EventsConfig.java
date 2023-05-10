package com.example.sidepot.global.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EventsConfig {
    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventsInitializer(){
        return () -> Events.setPublisher(applicationContext);
    }
}
