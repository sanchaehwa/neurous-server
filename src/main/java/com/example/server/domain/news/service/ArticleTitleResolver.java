package com.example.server.domain.news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleTitleResolver {

    public String resolveFullTitleOrFallback(String originalUrl, String fallbackTitle) {
        if (originalUrl == null || originalUrl.isBlank()) return fallbackTitle;

        try {
            Document doc = Jsoup.connect(originalUrl)
                    .userAgent("Mozilla/5.0 (compatible; NeuroS/1.0; +https://example.com)")
                    .timeout((int) Duration.ofSeconds(7).toMillis())
                    .followRedirects(true)
                    .get();

            String ogTitle = doc.select("meta[property=og:title]").attr("content");
            if (ogTitle != null && !ogTitle.isBlank()) return ogTitle.trim();

            String titleTag = doc.title();
            if (titleTag != null && !titleTag.isBlank()) return titleTag.trim();

            return fallbackTitle;

        } catch (Exception e) {
            log.warn("[title] fetch failed. url={}, fallback used. cause={}", originalUrl, e.toString());
            return fallbackTitle;
        }
    }

    public boolean looksTruncated(String title) {
        if (title == null) return false;
        String t = title.trim();
        return t.endsWith("...") || t.endsWith("…") || t.endsWith("…");
    }
}