package com.ashoumar.gateway_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {


    @RequestMapping("/fallback")
    public Mono<String> fallBack(){
        return Mono.just("an error occurred");
    }
}
