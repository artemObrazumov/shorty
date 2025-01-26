package com.artemobrazumov.shorty.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationConfig {

    @Bean
    public RegistrationConfigurer registrationConfigurer() {
        return new RegistrationConfigurer();
    }
}
