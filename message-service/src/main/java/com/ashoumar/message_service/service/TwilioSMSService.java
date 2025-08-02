package com.ashoumar.message_service.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSMSService {

    @Value("${twilio.phone.from}")
    private String fromPhone;

    public void sendSMS(String to, String body) {
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromPhone),
                body
        ).create();
    }
}
