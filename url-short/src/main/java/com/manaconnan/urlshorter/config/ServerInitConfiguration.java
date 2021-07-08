package com.manaconnan.urlshorter.config;

import com.manaconnan.urlshorter.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/4
 * @Version 1.0
 */
@Configuration
public class ServerInitConfiguration {
    @Value("${machineId}")
    private int machineId;
    @Value("${dataCenterId}")
    private int dataCenterId;

    @Bean("idGenerator")
    SnowFlake snowFlake(){
        return new SnowFlake(dataCenterId,machineId);
    }
}
