package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReadContentRepository extends JpaRepository<ReadContentRepository, Integer> {

    @Query(value = """
            SELECT c.*
            FROM read_content rc
            JOIN content c ON c.content_id = rc.content_id
            WHERE rc.user_id = :userId
            ORDER BY rc.read_content_id DESC
            """, nativeQuery = true)
    List<Content> findReadContentsByUserId(Long userId, Pageable pageable);
}
