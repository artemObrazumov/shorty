package com.artemobrazumov.shorty.short_url.service;

import com.artemObrazumov.token.entity.UserEntity;
import com.artemobrazumov.shorty.short_url.exceptions.DuplicateShortUrlException;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlDTO;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlResponseDTO;
import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.exceptions.ShortUrlNotFoundException;
import com.artemobrazumov.shorty.short_url.factory.ShortUrlStringGenerator;
import com.artemobrazumov.shorty.short_url.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

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
        ShortUrl shortUrl = new ShortUrl(null, new UserEntity(userId), shortUrlDTO.url(), shortUrlString,
                shortUrlDTO.password());
        shortUrl = shortUrlRepository.save(shortUrl);
        return new ShortUrlResponseDTO(shortUrl.getId(), shortUrl.getAuthor().getId(), shortUrl.getRealUrl(),
                shortUrl.getShortUrl());
    }

    public ShortUrl getShortUrl(String shortUrlString) {
        return shortUrlRepository.findByShortUrl(shortUrlString).orElseThrow(() ->
                new ShortUrlNotFoundException(shortUrlString));
    }
}
