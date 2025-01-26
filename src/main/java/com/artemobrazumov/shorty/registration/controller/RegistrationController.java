package com.artemobrazumov.shorty.registration.controller;

import com.artemobrazumov.shorty.registration.dto.RegistrationDTO;
import com.artemobrazumov.shorty.registration.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public void register(@RequestBody @Valid RegistrationDTO registrationDTO) {
        registrationService.registerUser(registrationDTO);
    }
}
