package com.example.server.domain.content.controller;

import com.example.server.domain.content.dto.ContentResponse;
import com.example.server.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/explore")
    public ResponseEntity<Map<String,List<ContentResponse>>> getExploreContent(
            @AuthenticationPrincipal int userId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ){

        Map<String, List<ContentResponse>> result = contentService.getExploreContent(userId, page);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/today")
    public ResponseEntity<List<ContentResponse>> getTodayContent(
            @AuthenticationPrincipal int userId
    ){
        List<ContentResponse> result = contentService.getTodayContent(userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail")
    public ResponseEntity<ContentResponse> getContentDetail(@RequestParam("contentId") int contentId){
        ContentResponse contentResponse = contentService.getContentDetail(contentId);

        return ResponseEntity.ok(contentResponse);
    }
}