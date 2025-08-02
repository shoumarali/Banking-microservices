package com.ashoumar.gateway_server.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitingConfig {

    @Bean
    public RedisRateLimiter redisRateLimiter(){

        return new RedisRateLimiter(20, 40);
    }

    @Bean
    KeyResolver userKeyResolver(){
        return exchange -> Mono.justOrEmpty(exchange
                .getRequest()
                .getHeaders()
                .getFirst("user")).defaultIfEmpty("anonymous");
    }
}
