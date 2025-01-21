package com.artemobrazumov.shorty.short_url.entity;

import com.artemObrazumov.token.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity author;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "real_url", nullable = false)
    private String realUrl;

    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrl;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public ShortUrl() {
    }

    public ShortUrl(Long id, UserEntity author, String name, String realUrl, String shortUrl, String password) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.realUrl = realUrl;
        this.shortUrl = shortUrl;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}