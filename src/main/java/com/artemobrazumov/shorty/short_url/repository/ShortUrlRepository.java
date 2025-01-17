package com.artemobrazumov.shorty.short_url.repository;

import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    boolean existsByShortUrl(String shortUrl);
}
