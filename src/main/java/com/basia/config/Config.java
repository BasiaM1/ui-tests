package com.basia.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.basia"})
public class Config {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Bean
    public ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

}
