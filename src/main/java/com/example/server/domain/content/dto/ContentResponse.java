package com.example.server.domain.content.dto;

import com.example.server.domain.content.entity.Content;
import lombok.Builder;

@Builder
public record ContentResponse(Integer contentId,
                              String title,
                              String content,
                              String category,
                              String contentDiff
) {
    public static ContentResponse from(Content c){
        return ContentResponse.builder()
                .contentId(c.getContentId())
                .title(c.getTitle())
                .content(c.getContent())
                .category(c.getContentCategory())
                .contentDiff(c.getContentDiff())
                .build();
    }
}
