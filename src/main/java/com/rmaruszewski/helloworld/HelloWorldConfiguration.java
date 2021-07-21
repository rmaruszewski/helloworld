package com.rmaruszewski.helloworld;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ComponentScan
public class HelloWorldConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
