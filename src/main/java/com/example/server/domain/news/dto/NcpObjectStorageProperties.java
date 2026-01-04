package com.example.server.domain.news.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ncp.object-storage")
public record NcpObjectStorageProperties(
        String endpoint,
        String regionName,
        String accessKey,
        String secretKey,
        String bucket,
        String prefix,
        String publicBaseUrl
) {}
