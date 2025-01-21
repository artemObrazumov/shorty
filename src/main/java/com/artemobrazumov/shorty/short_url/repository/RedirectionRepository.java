package com.artemobrazumov.shorty.short_url.repository;

import com.artemobrazumov.shorty.short_url.entity.Redirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RedirectionRepository extends JpaRepository<Redirection, Long> {

    @Query("SELECT r FROM Redirection r WHERE r.shortUrl.id = :id")
    List<Redirection> findByShortUrlId(Long id);
}
