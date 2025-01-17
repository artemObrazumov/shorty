package com.artemobrazumov.shorty.short_url.filter;

import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URISyntaxException;

public class ShortUrlPasswordFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/r/*", HttpMethod.GET.name());

    private final ShortUrlService shortUrlService;

    public ShortUrlPasswordFilter(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            try {
                String shortUrlString = extractShortUrlStringFromRequest(request);
                ShortUrl shortUrl = shortUrlService.getShortUrl(shortUrlString);
                String password = shortUrl.getPassword();
                if (password != null) {
                    if (request.getParameter("p") == null ||
                            !request.getParameter("p").equals(shortUrl.getPassword())) {
                        response.sendRedirect(String.format("/%s/password", shortUrl.getShortUrl()));
                    }
                }
                response.sendRedirect(shortUrl.getRealUrl());
            } catch (Exception e) {

            }
        }

        chain.doFilter(request, response);
    }

    private String extractShortUrlStringFromRequest(HttpServletRequest request) throws URISyntaxException {
        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        if (parts.length < 2) {
            throw new URISyntaxException(uri, "URI should contain at least 2 parts splitted by '/'");
        }
        return parts[parts.length - 1];
    }
}
