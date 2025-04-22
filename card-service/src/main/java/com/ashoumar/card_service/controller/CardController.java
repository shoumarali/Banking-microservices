package com.ashoumar.card_service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class CardController {

    @GetMapping("/fetch")
    public String fetchCard(){
        return "Card";
    }
}
