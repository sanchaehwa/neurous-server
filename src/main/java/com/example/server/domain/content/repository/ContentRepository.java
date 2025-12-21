package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    List<Content> findByContentDiffAndContentCategory(String contentDiff, String contentCategory, Pageable pageable);

    List<Content> findByContentDiffOrderByContentIdDesc(String contentDiff, Pageable pageable);

    @Query(value = """
            SELECT *
            FROM content
            WHERE content_diff = :ContentDiff
              AND content_category = :category
              AND NOT EXISTS (
                                SELECT 1
                                FROM read_content rc
                                WHERE rc.user_id = :userId
                                  AND rc.content_id = c.content_id
                            )
            ORDER BY RAND()
            LIMIT 1
            """, nativeQuery = true)
    Optional<Content> findRandomUnreadByContentDiffAndCategory(int userId, String ContentDiff, String category);

    @Query(value = """
            SELECT *
            FROM content
            WHERE content_diff = :ContentDiff
              AND content_category = :category
              AND content_id NOT IN (:excludedIds)
              AND NOT EXISTS (
                                SELECT 1
                                FROM read_content rc
                                WHERE rc.user_id = :userId
                                  AND rc.content_id = c.content_id
                            )
            ORDER BY RAND()
            LIMIT 1
            """, nativeQuery = true)
    Optional<Content> findRandomUnreadByContentDiffAndCategoryExcludeIds(int userId, String ContentDiff, String category, List<Integer> excludedIds);
}

