package com.artemobrazumov.shorty.short_url.repository;

import com.artemobrazumov.shorty.short_url.entity.Redirection;
import com.artemobrazumov.shorty.short_url.repository.redirection_stats.RedirectionCountStatsRow;
import com.artemobrazumov.shorty.short_url.repository.redirection_stats.RedirectionCountiesStatsRow;
import com.artemobrazumov.shorty.short_url.repository.redirection_stats.RedirectionReferersStatsRow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RedirectionRepository extends JpaRepository<Redirection, Long> {

    @Query("SELECT r FROM Redirection r WHERE r.shortUrl.id = :id ORDER BY r.redirectionTime DESC")
    List<Redirection> findByShortUrlId(Long id, Pageable pageable);

    @Query(value = """
            SELECT
            CAST(time AS TIMESTAMP),
            CAST(COUNT(CASE WHEN r.id IS NOT NULL THEN 1 END) AS INTEGER) AS total,
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

    @Query(value = """
            SELECT r.country, CAST(COUNT(*) AS INTEGER) AS count
            FROM redirections r
            WHERE r.country IS NOT NULL
            AND r.short_url_id = :id
            AND r.redirection_time >= DATE_TRUNC(:dateTrunc, CAST(:from AS TIMESTAMP))
            AND r.redirection_time <= DATE_TRUNC(:dateTrunc, CAST(:to AS TIMESTAMP))
            GROUP BY r.country
            ORDER BY count DESC;
            """,
            nativeQuery = true)
    List<RedirectionCountiesStatsRow> getRedirectionCountriesStats(Long id, String dateTrunc,
                                                                   LocalDateTime from, LocalDateTime to);

    @Query(value = """
            SELECT
            regexp_replace(r.referer, '^(https?:\\/\\/([^\\s\\/]+)\\.([^\\s\\/]+)).*', '\\1') AS url,
            CAST(COUNT(*) AS INTEGER) AS count
            FROM redirections r
            WHERE r.referer IS NOT NULL
            AND r.short_url_id = :id
            AND r.redirection_time >= DATE_TRUNC(:dateTrunc, CAST(:from AS TIMESTAMP))
            AND r.redirection_time <= DATE_TRUNC(:dateTrunc, CAST(:to AS TIMESTAMP))
            GROUP BY url
            ORDER by count DESC;
            """,
        nativeQuery = true)
    List<RedirectionReferersStatsRow> getRedirectionReferersStats(Long id, String dateTrunc,
                                                                  LocalDateTime from, LocalDateTime to);

    @Query(value = """
            SELECT CAST(COUNT(*) AS INTEGER)
            FROM redirections r
            WHERE r.short_url_id = :id;
            """,
        nativeQuery = true)
    Integer getRedirectionsOfShortUrlCount(Long id);
}
