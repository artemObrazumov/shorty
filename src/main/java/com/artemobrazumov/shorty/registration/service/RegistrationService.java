package com.artemobrazumov.shorty.registration.service;

import com.artemObrazumov.token.entity.UserEntity;
import com.artemObrazumov.token.repository.UserRepository;
import com.artemobrazumov.shorty.registration.dto.RegistrationDTO;
import com.artemobrazumov.shorty.registration.exceptions.DuplicateUserException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationDTO registrationDTO) {
        if (userRepository.findByName(registrationDTO.name()).isPresent()) {
            throw new DuplicateUserException(registrationDTO.name());
        }
        UserEntity userEntity = new UserEntity(null, registrationDTO.name(),
                passwordEncoder.encode(registrationDTO.password()));
        userRepository.save(userEntity);
    }
}
