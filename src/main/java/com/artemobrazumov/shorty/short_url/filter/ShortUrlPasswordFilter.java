package com.artemobrazumov.shorty.short_url.filter;

import com.artemobrazumov.shorty.short_url.dto.RedirectionDTO;
import com.artemobrazumov.shorty.short_url.entity.Redirection;
import com.artemobrazumov.shorty.short_url.entity.RedirectionStatus;
import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.service.RedirectionService;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class ShortUrlPasswordFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/r/*", HttpMethod.GET.name());

    private final ShortUrlService shortUrlService;
    private final RedirectionService redirectionService;

    public ShortUrlPasswordFilter(ShortUrlService shortUrlService, RedirectionService redirectionService) {
        this.shortUrlService = shortUrlService;
        this.redirectionService = redirectionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            try {
                String shortUrlString = extractShortUrlStringFromRequest(request);
                ShortUrl shortUrl = shortUrlService.findShortUrlByShortUrlString(shortUrlString);
                Redirection redirection = getRedirectionEntry(request, shortUrl);

                String password = shortUrl.getPassword();
                if (password != null) {
                    if (request.getParameter("p") == null || !request.getParameter("p").equals(password)) {
                        if (request.getParameter("p") != null && !password.equals(request.getParameter("p"))) {
                            updateRedirectionStatus(redirection, RedirectionStatus.INCORRECT_PASSWORD);
                        }
                        response.sendRedirect(UriComponentsBuilder
                                .fromPath(String.format("/%s/password", shortUrl.getShortUrl()))
                                .queryParam("r_id", redirection.getId())
                                .toUriString());
                        return;
                    }
                }
                response.sendRedirect(shortUrl.getRealUrl());
                updateRedirectionStatus(redirection, RedirectionStatus.SUCCESSFUL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }

    private Redirection getRedirectionEntry(HttpServletRequest request, ShortUrl shortUrl) {
        if (request.getParameter("r_id") == null) {
            return saveRedirection(request, shortUrl);
        } else {
            return redirectionService.findRedirectionById(Long.parseLong(request.getParameter("r_id")));
        }
    }

    private void updateRedirectionStatus(Redirection redirection, RedirectionStatus redirectionStatus) {
        redirection.setStatus(redirectionStatus);
        redirectionService.saveRedirection(redirection);
    }

    private String extractShortUrlStringFromRequest(HttpServletRequest request) throws URISyntaxException {
        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        if (parts.length < 2) {
            throw new URISyntaxException(uri, "URI should contain at least 2 parts splitted by '/'");
        }
        return parts[parts.length - 1];
    }

    private Redirection saveRedirection(HttpServletRequest request, ShortUrl shortUrl) {
        RedirectionDTO redirectionDTO = generateRedirectionDTO(request, shortUrl);
        return redirectionService.saveRedirection(redirectionDTO);
    }

    private RedirectionDTO generateRedirectionDTO(HttpServletRequest request, ShortUrl shortUrl) {
        String referer = request.getHeader("Referer");
        return new RedirectionDTO(
                LocalDateTime.now(),
                shortUrl,
                null,
                request.getRemoteAddr(),
                referer
        );
    }
}
