package com.ashoumar.gateway_server.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class GatewayRoutingConfig {


    @Bean
    public RouteLocator bankingRouteConfig(
            RouteLocatorBuilder routeLocatorBuilder,
            RedisRateLimiter redisRateLimiter,
            KeyResolver userKeyResolver

    ){
        return routeLocatorBuilder
                .routes()
                .route(p -> p
                        .path("/banking/accounts/**")
                        .filters(f -> f
                                .rewritePath("banking/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter)
                                        .setKeyResolver(userKeyResolver)
                                )
                        )
                        .uri("lb://ACCOUNT-SERVICE")
                )
                .route(p -> p
                        .path("/banking/cards/**")
                        .filters(f -> f
                                .rewritePath("banking/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("cardServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter)
                                        .setKeyResolver(userKeyResolver)
                                )
                        )
                        .uri("lb://CARD-SERVICE")
                )
                .route(p -> p
                        .path("/banking/loans/**")
                        .filters(f -> f
                                .rewritePath("banking/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("loanServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter)
                                        .setKeyResolver(userKeyResolver)
                                )
                        )
                        .uri("lb://LOAN-SERVICE")
                )
                .build();
    }
}