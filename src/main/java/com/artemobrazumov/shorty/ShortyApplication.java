package com.artemobrazumov.shorty;

import com.artemObrazumov.token.JWTAuthConfig;
import com.artemObrazumov.token.entity.UserAuthority;
import com.artemObrazumov.token.entity.UserEntity;
import com.artemObrazumov.token.repository.UserAuthorityRepository;
import com.artemObrazumov.token.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
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

	@Bean
	public CommandLineRunner addSampleUserWithAuthorities(
			UserRepository userRepository,
			UserAuthorityRepository userAuthorityRepository
	) {
		return args -> {
			if (userRepository.findById(1L).isEmpty()) {
				var user = userRepository.save(new UserEntity(null, "j.jameson", "{noop}password"));
				userAuthorityRepository.save(new UserAuthority(null, user, "ROLE_MANAGER"));
			}
		};
	}
}
