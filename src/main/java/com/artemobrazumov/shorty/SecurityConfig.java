package com.artemobrazumov.shorty;

import com.artemObrazumov.token.JWTAuthConfigurer;
import com.artemobrazumov.shorty.short_url.ShortUrlConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JWTAuthConfigurer jwtAuthConfigurer,
                                                   ShortUrlConfigurer shortUrlConfigurer) throws Exception {
        return http
                .with(jwtAuthConfigurer, Customizer.withDefaults())
                .with(shortUrlConfigurer, Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests.requestMatchers("/jwt").permitAll())
                .build();
    }
}
