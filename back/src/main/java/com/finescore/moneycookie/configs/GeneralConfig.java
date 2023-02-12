package com.finescore.moneycookie.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.repository.ClosedDayRepository;
import com.finescore.moneycookie.repository.ItemRepository;
import com.finescore.moneycookie.repository.MemberRepository;
import com.finescore.moneycookie.repository.SectionRepository;
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
    public MemberRepository memberRepository() {
        return new MemberRepository(dataSource);
    }

    @Bean
    public SectionRepository sectionRepository() {
        return new SectionRepository(dataSource);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new ItemRepository(dataSource);
    }

    @Bean
    public ClosedDayRepository infoRepository() {
        return new ClosedDayRepository(dataSource);
    }
}
