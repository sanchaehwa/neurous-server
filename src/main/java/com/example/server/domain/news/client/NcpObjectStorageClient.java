package com.example.server.domain.news.client;

import com.example.server.domain.news.dto.NcpObjectStorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class NcpObjectStorageClient {

    private final S3Client s3;
    private final NcpObjectStorageProperties props;

    public boolean exists(String key) {
        try {
            s3.headObject(HeadObjectRequest.builder()
                    .bucket(props.bucket())
                    .key(key)
                    .build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (S3Exception e) {
            throw e;
        }
    }

    public String putPublicPng(String key, byte[] pngBytes) {
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(props.bucket())
                .key(key)
                .contentType("image/png")
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3.putObject(req, RequestBody.fromBytes(pngBytes));
        return buildPublicUrl(key);
    }

    public String buildKey(String filename) {
        String p = (props.prefix() == null || props.prefix().isBlank()) ? "" : props.prefix().trim();
        if (p.isEmpty()) return filename;
        if (p.endsWith("/")) return p + filename;
        return p + "/" + filename;
    }

    public String buildPublicUrl(String key) {
        String base = (props.publicBaseUrl() != null && !props.publicBaseUrl().isBlank())
                ? props.publicBaseUrl().trim()
                : ("https://" + props.bucket() + ".kr.object.ncloudstorage.com");

        String safeKey = key.replace(" ", "%20");
        return base + "/" + safeKey;
    }
}
