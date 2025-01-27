package com.artemobrazumov.shorty.short_url.repository;

import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    boolean existsByShortUrl(String shortUrl);
    Optional<ShortUrl> findByShortUrl(String shortUrl);
    List<ShortUrl> findByAuthorId(Long authorId, Pageable pageable);
}
