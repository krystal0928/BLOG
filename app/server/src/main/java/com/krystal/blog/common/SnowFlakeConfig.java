package com.krystal.blog.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "snowflake", name = {"workerId", "dataCenterId"})
@Configuration
public class SnowFlakeConfig {
    @Value("${snowflake.workerId?:1}")
    private Long workerId;
    @Value("${snowflake.dataCenterId?:1}")
    private Long dataCenterId;

    @Bean
    public SnowFlakeTemplate snowFlakeTemplate() {
        return SnowFlakeTemplate.builder()
                .workerId(workerId)
                .dataCenterId(dataCenterId)
                .build();
    }
}
