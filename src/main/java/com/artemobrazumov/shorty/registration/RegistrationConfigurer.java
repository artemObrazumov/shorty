package com.artemobrazumov.shorty.registration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class RegistrationConfigurer extends AbstractHttpConfigurer<RegistrationConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.authorizeHttpRequests(authorizer -> {
            authorizer.requestMatchers("/registration**").permitAll();
        });

        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(
                    new AntPathRequestMatcher("/registration**", HttpMethod.POST.name()));
        }
    }
}
