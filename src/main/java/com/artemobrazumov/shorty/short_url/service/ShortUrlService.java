package com.artemobrazumov.shorty.short_url.service;

import com.artemObrazumov.token.entity.UserEntity;
import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlDetailsDTO;
import com.artemobrazumov.shorty.short_url.exceptions.DuplicateShortUrlException;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlDTO;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlResponseDTO;
import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.exceptions.NotShortUrlAuthorException;
import com.artemobrazumov.shorty.short_url.exceptions.ShortUrlNotFoundException;
import com.artemobrazumov.shorty.short_url.factory.ShortUrlStringGenerator;
import com.artemobrazumov.shorty.short_url.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlStringGenerator shortUrlStringGenerator;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, ShortUrlStringGenerator shortUrlStringGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlStringGenerator = shortUrlStringGenerator;
    }

    public ShortUrlResponseDTO createShortUrl(Long userId, ShortUrlDTO shortUrlDTO) {
        String shortUrlString;
        if (shortUrlDTO.shortUrl() == null) {
            shortUrlString = shortUrlStringGenerator.generateRandomString();
        } else {
            shortUrlString = shortUrlDTO.shortUrl();
        }
        if (shortUrlRepository.existsByShortUrl(shortUrlString)) {
            throw new DuplicateShortUrlException(shortUrlString);
        }
        ShortUrl shortUrl = new ShortUrl(null, new UserEntity(userId), shortUrlDTO.name(), shortUrlDTO.url(),
                shortUrlString, shortUrlDTO.password());
        shortUrl = shortUrlRepository.save(shortUrl);
        return new ShortUrlResponseDTO(shortUrl.getId(), shortUrl.getName(), shortUrl.getRealUrl(),
                shortUrl.getShortUrl());
    }

    public ShortUrlDetailsDTO getShortUrlDetails(TokenUser user, Long id) {
        ShortUrl shortUrl = findShortUrlById(id);
        if (!Objects.equals(shortUrl.getAuthor().getId(), user.getUserId())) {
            throw new NotShortUrlAuthorException();
        }
        Boolean isProtected = (shortUrl.getPassword() != null);
        return new ShortUrlDetailsDTO(shortUrl.getId(), shortUrl.getName(), shortUrl.getRealUrl(),
                shortUrl.getShortUrl(), isProtected, shortUrl.getActive());
    }

    public ShortUrl findShortUrlById(Long id) {
        return shortUrlRepository.findById(id).orElseThrow(() ->
                new ShortUrlNotFoundException(id));
    }

    public ShortUrl findShortUrlByShortUrlString(String shortUrlString) {
        return shortUrlRepository.findByShortUrl(shortUrlString).orElseThrow(() ->
                new ShortUrlNotFoundException(shortUrlString));
    }
}
