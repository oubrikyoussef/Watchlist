package com.openclassrooms.watchlist.actuator;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTraceActuatorConfiguration {

    @Bean
    public HttpExchangeRepository createTraceRepository() {
        return new InMemoryHttpExchangeRepository();
    }
}