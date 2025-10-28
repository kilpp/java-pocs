package com.gk.springaipoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class ChatConfigFunction {


    @Bean
    @Description("Checks the credit limit of a person by its age and income")
    public Function<CreditLimitInput, String> checkCreditLimit(CreditLimitService service) {
        return service::checkCreditLimit;
    }
}
