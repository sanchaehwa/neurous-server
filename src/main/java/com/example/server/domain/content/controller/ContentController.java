package com.example.server.domain.content.controller;

import com.example.server.domain.content.dto.ContentResponse;
import com.example.server.domain.content.dto.ContentDifficultyRequest;
import com.example.server.domain.content.dto.DifficultyRecommendResponse;
import com.example.server.domain.content.service.ContentService;
import com.example.server.global.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/explore")
    public ResponseEntity<Map<String,List<ContentResponse>>> getExploreContent(
            @CurrentUserId Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ){

        Map<String, List<ContentResponse>> result = contentService.getExploreContent(userId, page);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/today")
    public ResponseEntity<List<ContentResponse>> getTodayContent(
            @CurrentUserId Long userId
    ){
        List<ContentResponse> result = contentService.getTodayContent(userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail")
    public ResponseEntity<ContentResponse> getContentDetail(@RequestParam("contentId") int contentId){
        ContentResponse contentResponse = contentService.getContentDetail(contentId);

        return ResponseEntity.ok(contentResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContentResponse>> searchContent(
            @CurrentUserId Long userId,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page){
        List<ContentResponse> result = contentService.search(userId, keyword, page);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ContentResponse>> getReadHistory(
            @CurrentUserId Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<ContentResponse> result = contentService.getReadHistory(userId, page);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/evaluation")
    public ResponseEntity<DifficultyRecommendResponse> setContentEvaluation(
            @CurrentUserId Long userId,
            @RequestParam("contentId") int contentId,
            @RequestBody ContentDifficultyRequest difficulty){

        DifficultyRecommendResponse result = contentService.setDifficultyEvaluation(userId, contentId, difficulty);

        return ResponseEntity.ok(result);
    }
}