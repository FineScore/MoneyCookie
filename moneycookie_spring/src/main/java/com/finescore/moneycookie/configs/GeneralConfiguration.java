package com.finescore.moneycookie.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
