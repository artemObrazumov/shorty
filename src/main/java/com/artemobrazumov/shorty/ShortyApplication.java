package com.artemobrazumov.shorty;

import com.artemObrazumov.token.JWTAuthConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@Import(JWTAuthConfig.class)
public class ShortyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortyApplication.class, args);
	}
}
