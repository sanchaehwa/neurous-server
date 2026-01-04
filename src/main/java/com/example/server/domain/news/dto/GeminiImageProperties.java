package com.example.server.domain.news.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.gemini")
public record GeminiImageProperties(
        String apiKey,
        String baseUrl,
        String model,
        int timeoutMs,
        int maxRpm,
        int maxRetries,
        long baseBackoffMs,
        long maxBackoffMs
) {}
