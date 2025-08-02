package com.ashoumar.message_service.functions;

import com.ashoumar.message_service.dto.AccountsMsgDto;
import com.ashoumar.message_service.service.TwilioSMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger logger= LoggerFactory.getLogger(MessageFunctions.class);

    private final TwilioSMSService smsService;

    public MessageFunctions(TwilioSMSService smsService) {
        this.smsService = smsService;
    }


    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email(){
        return accountsMsgDto -> {
            logger.info("sending email with the details: {}", accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto, Long> sms(){
        return accountsMsgDto -> {
            logger.info("sending sms with the details: {}", accountsMsgDto.toString());

            String message = "Hello, your account " + accountsMsgDto.accountNumber() + " has been created.";
            smsService.sendSMS("+961" + accountsMsgDto.mobileNumber(), message);

            return accountsMsgDto.accountNumber();
        };
    }

}
