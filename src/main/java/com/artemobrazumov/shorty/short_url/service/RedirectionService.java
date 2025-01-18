package com.artemobrazumov.shorty.short_url.service;

import com.artemobrazumov.shorty.short_url.dto.RedirectionDTO;
import com.artemobrazumov.shorty.short_url.entity.Redirection;
import com.artemobrazumov.shorty.short_url.entity.RedirectionStatus;
import com.artemobrazumov.shorty.short_url.exceptions.RedirectionNotFoundException;
import com.artemobrazumov.shorty.short_url.repository.RedirectionRepository;
import org.springframework.stereotype.Service;

@Service
public class RedirectionService {

    private final RedirectionRepository redirectionRepository;

    public RedirectionService(RedirectionRepository redirectionRepository) {
        this.redirectionRepository = redirectionRepository;
    }

    public Redirection saveRedirection(RedirectionDTO redirectionDTO) {
        Redirection redirection = new Redirection(null, redirectionDTO.redirectionTime(),
                redirectionDTO.shortUrl(), RedirectionStatus.UNDEFINED, redirectionDTO.country(),
                redirectionDTO.ip(), redirectionDTO.referer());
        return redirectionRepository.save(redirection);
    }

    public Redirection saveRedirection(Redirection redirection) {
        return redirectionRepository.save(redirection);
    }

    public Redirection findRedirectionById(Long id) {
        return redirectionRepository.findById(id).orElseThrow(() ->
                new RedirectionNotFoundException(id));
    }
}
