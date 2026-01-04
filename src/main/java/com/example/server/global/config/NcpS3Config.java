package com.example.server.global.config;

import com.example.server.domain.news.dto.NcpObjectStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NcpS3Config {

    private final NcpObjectStorageProperties props;

    @Bean
    public S3Client ncpS3Client() {

        var cred = AwsBasicCredentials.create(
                must(props.accessKey(), "ncp.object-storage.access-key"),
                must(props.secretKey(), "ncp.object-storage.secret-key")
        );

        return S3Client.builder()
                .endpointOverride(URI.create(must(props.endpoint(), "ncp.object-storage.endpoint")))
                .region(Region.of(must(props.regionName(), "ncp.object-storage.region-name")))
                .credentialsProvider(StaticCredentialsProvider.create(cred))
                .build();
    }

    private static String must(String v, String name) {
        if (v == null || v.isBlank()) throw new IllegalStateException(name + " is blank");
        return v.trim();
    }
}
