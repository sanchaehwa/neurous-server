package com.example.server.domain.quiz.service;

import com.example.server.domain.content.entity.Content;
import com.example.server.domain.news.client.ClovaStudioClient;
import com.example.server.domain.news.dto.ClovaChatCompletionRequest;
import com.example.server.domain.news.dto.ClovaStudioProperties;
import com.example.server.domain.quiz.dto.response.QuizGenerateResult;
import com.example.server.domain.quiz.entity.Quiz;
import com.example.server.domain.quiz.entity.QuizChoice;
import com.example.server.domain.quiz.repository.QuizChoiceRepository;
import com.example.server.domain.quiz.repository.QuizRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizGenerationService {

    private final QuizRepository quizRepository;
    private final QuizChoiceRepository quizChoiceRepository;

    private final ClovaStudioClient clovaStudioClient;
    private final ClovaStudioProperties props;
    private final ObjectMapper objectMapper;

    @Value("${quiz.generation.max-retries:3}")
    private int maxRetries;

    @Transactional
    public int generateQuizForContent(Content content) {
        if (quizRepository.existsByContent_ContentId(content.getContentId())) {
            return 0;
        }

        QuizGenerateResult result = generateWithRetry(content);

        Quiz quiz = Quiz.builder()
                .question(result.question())
                .quizDiff(content.getContentLevel())
                .quizCategory(content.getContentCategory().name())
                .content(content)
                .build();

        Quiz saved = quizRepository.save(quiz);

        int answerNo = result.answerNo();
        List<String> choices = result.choices();

        for (int i = 0; i < choices.size(); i++) {
            int choiceNo = i + 1;
            quizChoiceRepository.save(
                    QuizChoice.builder()
                            .quiz(saved)
                            .choiceNo(choiceNo)
                            .choiceText(choices.get(i))
                            .isCorrect(choiceNo == answerNo)
                            .build()
            );
        }

        return 1;
    }

    private QuizGenerateResult generateWithRetry(Content content) {
        RuntimeException last = null;

        for (int i = 1; i <= maxRetries; i++) {
            try {
                String prompt = buildPrompt(content);
                String raw = callClova(prompt);
                String json = extractJson(raw);

                QuizGenerateResult r = objectMapper.readValue(json, QuizGenerateResult.class);

                r = normalizeTo3(r);

                validate(r);

                return r;

            } catch (Exception e) {
                last = new RuntimeException("quiz generation failed attempt=" + i + ", contentId=" + content.getContentId(), e);
                log.warn("[quiz] {}", last.getMessage());
            }
        }

        throw last != null ? last : new RuntimeException("quiz generation failed: unknown");
    }

    private String callClova(String userPrompt) {
        String system = """
            당신은 뉴스 학습 컨텐츠를 기반으로 객관식 퀴즈를 만드는 한국어 출제자입니다.

            출력은 반드시 JSON만 하세요. (설명/문장/코드펜스/마크다운 금지)
            JSON 스키마는 아래와 같습니다.
            {
              "question": "문제 문장 (1문장)",
              "choices": ["보기1","보기2","보기3"],
              "answerNo": 1
            }

            제약:
            - question은 1문장, 25~60자.
            - choices는 3개, 각 보기 8~25자.
            - answerNo는 1~3 정수.
            - 컨텐츠에 없는 정보를 만들지 마세요.
            """;

        ClovaChatCompletionRequest req = new ClovaChatCompletionRequest(
                props.model(),
                List.of(
                        new ClovaChatCompletionRequest.Message("system", system),
                        new ClovaChatCompletionRequest.Message("user", userPrompt)
                ),
                0.3,
                300
        );

        return clovaStudioClient.chat(req);
    }

    private String buildPrompt(Content content) {

        String body = safeTrim(content.getContent(), 2000);

        return """
            아래 학습 컨텐츠를 읽고, 내용 이해를 확인하는 객관식 문제 1개를 만드세요.
            난이도: %s
            카테고리: %s

            컨텐츠:
            %s
            """.formatted(content.getContentLevel(), content.getContentCategory(), body);
    }

    private void validate(QuizGenerateResult r) {
        if (r == null) throw new IllegalStateException("result null");
        if (isBlank(r.question())) throw new IllegalStateException("question blank");
        if (r.choices() == null || r.choices().size() != 3) throw new IllegalStateException("choices must be 3");
        for (String c : r.choices()) {
            if (isBlank(c)) throw new IllegalStateException("choice blank");
        }
        if (r.answerNo() < 1 || r.answerNo() > 3) throw new IllegalStateException("answerNo out of range");
    }

    private String extractJson(String raw) {
        if (raw == null) return "";
        String t = raw.trim();

        // ```json ... ``` 제거
        if (t.startsWith("```")) {
            int firstNl = t.indexOf('\n');
            if (firstNl > 0) t = t.substring(firstNl + 1);
            int lastFence = t.lastIndexOf("```");
            if (lastFence >= 0) t = t.substring(0, lastFence);
            t = t.trim();
        }

        // 앞뒤 잡텍스트가 섞여도 { ... }만 잘라내기
        int s = t.indexOf('{');
        int e = t.lastIndexOf('}');
        if (s >= 0 && e > s) return t.substring(s, e + 1).trim();

        return t;
    }

    private String safeTrim(String s, int max) {
        if (s == null) return "";
        String t = s.trim();
        return (t.length() <= max) ? t : t.substring(0, max);
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private QuizGenerateResult normalizeTo3(QuizGenerateResult r) {
        if (r == null) return null;
        if (r.choices() == null) return r;

        List<String> choices = r.choices().stream()
                .filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .toList();

        int answerNo = r.answerNo();

        if (choices.size() < 3) {
            return new QuizGenerateResult(r.question(), choices, answerNo);
        }

        if (choices.size() == 3) {
            int fixed = Math.min(Math.max(answerNo, 1), 3);
            return new QuizGenerateResult(r.question(), choices, fixed);
        }

        int ansIdx = answerNo - 1;
        if (ansIdx < 0 || ansIdx >= choices.size()) ansIdx = 0;

        java.util.ArrayList<String> normalized = new java.util.ArrayList<>(3);

        normalized.add(choices.get(ansIdx));

        for (int i = 0; i < choices.size() && normalized.size() < 3; i++) {
            if (i == ansIdx) continue;
            normalized.add(choices.get(i));
        }

        return new QuizGenerateResult(r.question(), normalized, 1);
    }

}