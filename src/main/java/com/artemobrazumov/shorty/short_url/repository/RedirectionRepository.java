package com.artemobrazumov.shorty.short_url.repository;

import com.artemobrazumov.shorty.short_url.entity.Redirection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedirectionRepository extends JpaRepository<Redirection, Long> {
}
