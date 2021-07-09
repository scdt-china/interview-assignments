package com.scdtchina.sdns.config;

import com.scdtchina.sdns.repository.LRUUrlPairsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Value("${sdns.urlPool.capacity}")
    private int capacity;

    @Bean
    public LRUUrlPairsRepository lruUrlPairsRepository() {
        LRUUrlPairsRepository repository = new LRUUrlPairsRepository(capacity);
        return repository;
    }

}
