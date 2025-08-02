package com.ashoumar.message_service.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void init() {
        System.out.println("SID: " + accountSid);
        System.out.println("TOKEN: " + authToken);
        Twilio.init(accountSid, authToken);
    }
}

