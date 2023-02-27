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
    public UserRepositoryJdbc memberRepository() {
        return new UserRepositoryJdbc(dataSource);
    }

    @Bean
    public SectionRepositoryJdbc sectionRepository() {
        return new SectionRepositoryJdbc(dataSource);
    }

    @Bean
    public ListedItemRepositoryJdbc listedItemRepository() {
        return new ListedItemRepositoryJdbc(dataSource);
    }

    @Bean
    public StockMarketClosedDaysRepositoryJdbc stockMarketClosedDaysRepository() {
        return new StockMarketClosedDaysRepositoryJdbc(dataSource);
    }

    @Bean
    public HoldingRepositoryJdbc holdingRepository() {
        return new HoldingRepositoryJdbc(dataSource);
    }

    @Bean
    public TotalRatingRepositoryJdbc totalRatingRepository() {
        return new TotalRatingRepositoryJdbc(dataSource);
    }

    @Bean
    public EvaluationRepositoryJdbc evaluationRepository() {
        return new EvaluationRepositoryJdbc(dataSource);
    }
}
