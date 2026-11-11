package com.sdt.banking.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationChainConfig {

    @Bean
    public TransferValidator transferValidationChain() {
        TransferValidator first = new PositiveAmountValidator();
        first.setNext(new DifferentAccountsValidator())
             .setNext(new ActiveAccountValidator())
             .setNext(new SufficientFundsValidator());
        return first;
    }
}
