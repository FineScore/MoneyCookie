package com.finescore.moneycookie.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class GeneralConfig {
    private final DataSource dataSource;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UserRepository memberRepository() {
        return new UserRepository(dataSource);
    }

    @Bean
    public SectionRepository sectionRepository() {
        return new SectionRepository(dataSource);
    }

    @Bean
    public ListedItemRepository listedItemRepository() {
        return new ListedItemRepository(dataSource);
    }

    @Bean
    public StockMarketClosedDaysRepository stockMarketClosedDaysRepository() {
        return new StockMarketClosedDaysRepository(dataSource);
    }

    @Bean
    public HoldingRepository holdingRepository() {
        return new HoldingRepository(dataSource);
    }

    @Bean
    public TotalRatingRepository totalRatingRepository() {
        return new TotalRatingRepository(dataSource);
    }

    @Bean
    public EvaluationRepository evaluationRepository() {
        return new EvaluationRepository(dataSource);
    }
}
