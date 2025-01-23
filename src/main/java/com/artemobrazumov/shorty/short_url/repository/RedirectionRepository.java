package com.artemobrazumov.shorty.short_url.repository;

import com.artemobrazumov.shorty.short_url.dto.GroupingMethod;
import com.artemobrazumov.shorty.short_url.entity.Redirection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;

public interface RedirectionRepository extends JpaRepository<Redirection, Long> {

    @Query("SELECT r FROM Redirection r WHERE r.shortUrl.id = :id ORDER BY r.redirectionTime DESC")
    List<Redirection> findByShortUrlId(Long id, Pageable pageable);

    @Query(value = """
            SELECT
            CAST(time AS TIMESTAMP),
            CAST(COUNT(CASE WHEN (r.id IS NOT NULL AND r.status = 0) THEN 1 END) AS INTEGER) AS undefined,
            CAST(COUNT(CASE WHEN (r.id IS NOT NULL AND r.status = 1) THEN 1 END) AS INTEGER) AS successful,
            CAST(COUNT(CASE WHEN (r.id IS NOT NULL AND r.status = 2) THEN 1 END) AS INTEGER) AS wrongPassword
            FROM GENERATE_SERIES(DATE_TRUNC(:dateTrunc, CAST(:from AS TIMESTAMP)), DATE_TRUNC(:dateTrunc, CAST(:to AS TIMESTAMP)), CAST(:interval AS INTERVAL)) AS time
            LEFT JOIN public.redirections r ON (time = DATE_TRUNC(:dateTrunc, r.redirection_time) AND r.short_url_id = :id)
            GROUP BY time
            ORDER BY time;
            """,
            nativeQuery = true)
    List<RedirectionCountStatsRow> getRedirectionCountStats(Long id, String interval, String dateTrunc,
                                                            LocalDateTime from, LocalDateTime to);
}
