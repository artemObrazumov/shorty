package com.artemobrazumov.shorty.short_url.service;

import com.artemObrazumov.token.entity.UserEntity;
import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.*;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.*;
import com.artemobrazumov.shorty.short_url.entity.Redirection;
import com.artemobrazumov.shorty.short_url.exceptions.DuplicateShortUrlException;
import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.exceptions.NotShortUrlAuthorException;
import com.artemobrazumov.shorty.short_url.exceptions.ShortUrlNotFoundException;
import com.artemobrazumov.shorty.short_url.factory.ShortUrlStringGenerator;
import com.artemobrazumov.shorty.short_url.repository.RedirectionRepository;
import com.artemobrazumov.shorty.short_url.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final RedirectionRepository redirectionRepository;
    private final ShortUrlStringGenerator shortUrlStringGenerator;

    @Value("${short-url.redirection-logs.page-size}")
    private Integer pageSize;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, RedirectionRepository redirectionRepository,
                           ShortUrlStringGenerator shortUrlStringGenerator) {
        this.shortUrlRepository = shortUrlRepository;
        this.redirectionRepository = redirectionRepository;
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

    public RedirectionsDTO getShortUrlRedirections(TokenUser user, Long id, Integer page) {
        ShortUrl shortUrl = findShortUrlById(id);
        if (!Objects.equals(shortUrl.getAuthor().getId(), user.getUserId())) {
            throw new NotShortUrlAuthorException();
        }
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<Redirection> redirectionEntities = redirectionRepository
                .findByShortUrlId(shortUrl.getId(), pageable);

        return new RedirectionsDTO(redirectionEntities
                .stream()
                .map(r -> new RedirectionResponseDTO(r.getId(), r.getRedirectionTime(), r.getStatus(),
                        r.getCountry(), r.getIp(), r.getReferer()))
                .toList());
    }

    public RedirectionCountDTO getRedirectionsCountStats(TokenUser user, Long id,
                                                         LocalDateTime from, LocalDateTime to) {
        ShortUrl shortUrl = findShortUrlById(id);
        if (!Objects.equals(shortUrl.getAuthor().getId(), user.getUserId())) {
            throw new NotShortUrlAuthorException();
        }
        GroupingMethod groupingMethod = estimateGroupingMethod(from, to);
        var items = redirectionRepository.getRedirectionCountStats(id, groupingMethod.getInterval(),
                groupingMethod.getDateTruncUnit(), from, to);
        return new RedirectionCountDTO(groupingMethod, items.stream()
                .map(row ->
                        new RedirectionCountItemDTO(row.time().toLocalDateTime(), row.total(), row.undefined(),
                                row.wrongPassword(), row.successful()))
                .toList()
        );
    }

    public RedirectionCountriesDTO getRedirectionsCountriesStats(TokenUser user, Long id,
                                                                 LocalDateTime from, LocalDateTime to) {
        ShortUrl shortUrl = findShortUrlById(id);
        if (!Objects.equals(shortUrl.getAuthor().getId(), user.getUserId())) {
            throw new NotShortUrlAuthorException();
        }
        GroupingMethod groupingMethod = estimateGroupingMethod(from, to);
        var items = redirectionRepository.getRedirectionCountriesStats(id, groupingMethod.getDateTruncUnit(),
                from, to);
        return new RedirectionCountriesDTO(items.stream()
                .map(row ->
                        new RedirectionCountriesItemDTO(row.country(), row.count()))
                .toList()
        );
    }

    public RedirectionRefererDTO getRedirectionsRefererStats(TokenUser user, Long id,
                                                               LocalDateTime from, LocalDateTime to) {
        ShortUrl shortUrl = findShortUrlById(id);
        if (!Objects.equals(shortUrl.getAuthor().getId(), user.getUserId())) {
            throw new NotShortUrlAuthorException();
        }
        GroupingMethod groupingMethod = estimateGroupingMethod(from, to);
        var items = redirectionRepository.getRedirectionReferersStats(id, groupingMethod.getDateTruncUnit(),
                from, to);
        return new RedirectionRefererDTO(items.stream()
                .map(row ->
                        new RedirectionRefererItemDTO(row.url(), row.count()))
                .toList()
        );
    }

    public ShortUrl findShortUrlById(Long id) {
        return shortUrlRepository.findById(id).orElseThrow(() ->
                new ShortUrlNotFoundException(id));
    }

    public ShortUrl findShortUrlByShortUrlString(String shortUrlString) {
        return shortUrlRepository.findByShortUrl(shortUrlString).orElseThrow(() ->
                new ShortUrlNotFoundException(shortUrlString));
    }

    private GroupingMethod estimateGroupingMethod(LocalDateTime from, LocalDateTime to) {
        GroupingMethod groupingMethod = GroupingMethod.BY_DAY;
        if (Duration.between(from, to).toDays() >= 30) {
            groupingMethod = GroupingMethod.BY_MONTH;
        }
        return groupingMethod;
    }
}
