package com.example.server.domain.news.service;

import com.example.server.domain.news.client.ClovaStudioClient;
import com.example.server.domain.news.dto.ClovaChatCompletionRequest;
import com.example.server.domain.news.dto.ClovaStudioProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleTranslationService {

    private final ClovaStudioClient clovaStudioClient;
    private final ClovaStudioProperties props;

    @Value("${title.translation.max-retries:2}")
    private int maxRetries;

    public String translateKoTitleToEn(String titleKo) {
        String ko = safeOneLine(titleKo);
        if (ko.isBlank()) return "";

        ko = clip(ko, 120);

        String system = """
                You are a professional translator.
                Translate the Korean news headline into a natural, concise English headline.
                Rules:
                - Output ONLY the translated headline (no quotes, no prefix like "English:")
                - Keep it short (8~14 words)
                - No emojis
                """;

        String user = "Korean headline: " + ko;

        for (int i = 0; i < maxRetries; i++) {
            try {
                ClovaChatCompletionRequest req = new ClovaChatCompletionRequest(
                        props.model(),
                        List.of(
                                new ClovaChatCompletionRequest.Message("system", system),
                                new ClovaChatCompletionRequest.Message("user", user)
                        ),
                        0.2,
                        80
                );

                String raw = clovaStudioClient.chat(req);
                String en = normalizeTranslation(raw);
                if (!en.isBlank()) return clip(en, 140);
            } catch (Exception ignored) {}
        }

        return "";
    }

    private String normalizeTranslation(String s) {
        if (s == null) return "";
        String t = s.replaceAll("\\s+", " ").trim();

        t = t.replaceFirst("^(English|EN|Translation)\\s*:\\s*", "").trim();

        if ((t.startsWith("\"") && t.endsWith("\"")) || (t.startsWith("“") && t.endsWith("”"))) {
            t = t.substring(1, t.length() - 1).trim();
        }
        return t;
    }

    private String safeOneLine(String s) {
        if (s == null) return "";
        return s.replaceAll("\\s+", " ").trim();
    }

    private String clip(String s, int maxLen) {
        if (s == null) return "";
        return (s.length() <= maxLen) ? s : s.substring(0, maxLen);
    }
}