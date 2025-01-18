package com.artemobrazumov.shorty.short_url.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "redirection_logs")
public class Redirection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "redirection_time", nullable = false)
    private LocalDateTime redirectionTime;

    @ManyToOne(targetEntity = ShortUrl.class)
    @JoinColumn(name = "short_url_id", nullable = false)
    private ShortUrl shortUrl;

    @Column(name = "status", nullable = false)
    private RedirectionStatus status;

    @Column(name = "country")
    private String country;

    @Column(name = "ip")
    private String ip;

    @Column(name = "referer")
    private String referer;

    public Redirection() {
    }

    public Redirection(Long id, LocalDateTime redirectionTime, ShortUrl shortUrl, RedirectionStatus status,
                       String country, String ip, String referer) {
        this.id = id;
        this.redirectionTime = redirectionTime;
        this.shortUrl = shortUrl;
        this.status = status;
        this.country = country;
        this.ip = ip;
        this.referer = referer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRedirectionTime() {
        return redirectionTime;
    }

    public void setRedirectionTime(LocalDateTime redirectionTime) {
        this.redirectionTime = redirectionTime;
    }

    public ShortUrl getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(ShortUrl shortUrl) {
        this.shortUrl = shortUrl;
    }

    public RedirectionStatus getStatus() {
        return status;
    }

    public void setStatus(RedirectionStatus status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
