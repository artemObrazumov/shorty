package com.artemobrazumov.shorty.short_url.entity;

import com.artemObrazumov.token.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity author;

    @Column(name = "real_url", nullable = false)
    private String realUrl;

    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrl;

    public ShortUrl() {
    }

    public ShortUrl(Long id, UserEntity author, String realUrl, String shortUrl) {
        this.id = id;
        this.author = author;
        this.realUrl = realUrl;
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
