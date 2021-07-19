package com.challenge.banktransactions;

import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main class.
 */
@SpringBootApplication
public class BankTransactionsApplication extends SpringBootServletInitializer {

    // Bean declarations to use a single instances throughout the application.
    @Bean
    public IBANValidator getIBANValidator() {
        return new IBANValidator();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BankTransactionsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BankTransactionsApplication.class, args);
    }

}
