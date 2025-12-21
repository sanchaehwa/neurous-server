package com.example.server.domain.content.service;

import com.example.server.domain.content.dto.ContentResponse;
import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.repository.ContentRepository;
import com.example.server.domain.content.repository.ReadContentRepository;
import com.example.server.domain.content.repository.UserInterestRepository;
import com.example.server.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;
    private final ReadContentRepository readContentRepository;

    private static final List<String> CATEGORIES = List.of("정치", "경제", "사회", "생활/문화", "IT/과학", "세계");
    private static final int RESULT_SIZE = 3;

    /**
     * 탐색 페이지 컨텐츠 조회
     */
    public Map<String, List<ContentResponse>> getExploreContent(int userId, int page){
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        String ContentDiff = userRepository.findContentDiffByUserId(userId)
                .orElse("초급");

        Map<String, List<ContentResponse>> result = new LinkedHashMap<>();

        for(String category : CATEGORIES){
            List<Content> contents = contentRepository.findByContentDiffAndContentCategory(ContentDiff, category, pageRequest);

            result.put(category, contents.stream().map(ContentResponse::from).toList());
        }

        return result;
    }

}
