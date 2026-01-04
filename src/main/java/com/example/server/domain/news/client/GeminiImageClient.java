package com.example.server.domain.news.client;

import com.example.server.domain.news.dto.GeminiImageProperties;
import com.example.server.global.exception.model.GeminiNoImageException;
import com.example.server.global.exception.model.GeminiRateLimitException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiImageClient {

    private final GeminiImageProperties props;
    private final ObjectMapper om = new ObjectMapper();

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public byte[] generatePng(String prompt) {
        log.info("[gemini] model={}, keyPresent={}", props.model(),
                props.apiKey() != null && !props.apiKey().isBlank());

        try {
            return doCall(prompt);
        } catch (GeminiRateLimitException | GeminiNoImageException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gemini image generation error", e);
        }
    }

    private byte[] doCall(String prompt) throws Exception {
        String url = props.baseUrl()
                + "/v1beta/models/" + props.model()
                + ":generateContent";

        String body = """
        {
          "contents": [{
            "parts": [{"text": %s}]
          }],
          "generationConfig": {
            "responseModalities": ["TEXT", "IMAGE"]
          }
        }
        """.formatted(om.valueToTree(prompt).toString());

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(props.timeoutMs()))
                .header("x-goog-api-key", props.apiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
        int status = res.statusCode();
        String json = res.body();

        if (status == 429) throw new GeminiRateLimitException(json);
        if (status < 200 || status >= 300) {
            throw new RuntimeException("Gemini failed status=" + status + ", body=" + json);
        }

        JsonNode root = om.readTree(json);

        JsonNode parts = root.at("/candidates/0/content/parts");
        if (parts == null || !parts.isArray()) {
            throw new GeminiNoImageException("Unexpected response(no parts): " + json);
        }

        for (JsonNode part : parts) {
            JsonNode inlineData = part.get("inlineData");
            if (inlineData != null && inlineData.get("data") != null) {
                String b64 = inlineData.get("data").asText();
                return Base64.getDecoder().decode(b64);
            }
        }

        throw new GeminiNoImageException(json);
    }
}
