package com.dilip.evaluation.abstractset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ISetServiceConfig {
    @Value("${spring.application.initialcapacity}")
    private String initialCapacity;

    @Value("${spring.application.loadfactor}")
    private String loadFactor;

    @Bean
    public ISetService abstractSetServiceImpl () {
        return new AbstractSetServiceImpl(initialCapacity, loadFactor);
    }
}
