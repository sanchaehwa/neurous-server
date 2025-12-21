package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    List<Content> findByContentDiffAndContentCategory(String contentDiff, String contentCategory, Pageable pageable);
}

