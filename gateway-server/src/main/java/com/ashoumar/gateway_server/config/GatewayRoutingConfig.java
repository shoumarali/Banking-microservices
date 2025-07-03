package com.ashoumar.gateway_server.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class GatewayRoutingConfig {


    @Bean
    public RouteLocator bankingRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder
                .routes()
                .route(p -> p
                        .path("/banking/accounts/**")
                        .filters(f -> f
                                .rewritePath("banking/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://ACCOUNT-SERVICE")
                )
                .route(p -> p
                        .path("/banking/cards/**")
                        .filters(f -> f
                                .rewritePath("banking/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://CARD-SERVICE")
                )
                .route(p -> p
                        .path("/banking/loans/**")
                        .filters(f -> f
                                .rewritePath("banking/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://LOAN-SERVICE")
                )
                .build();
    }
}
