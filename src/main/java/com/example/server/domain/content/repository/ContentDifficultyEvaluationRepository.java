package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.ContentDifficultyEvaluation;
import com.example.server.domain.content.entity.vo.ContentDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ContentDifficultyEvaluationRepository extends JpaRepository<ContentDifficultyEvaluation, Integer> {
    Optional<ContentDifficultyEvaluation> findByUserIdAndContentId(Long userId, int contentId);

    @Query("""
        select count(e)
        from ContentDifficultyEvaluation e
        where e.userId = :userId
          and e.contentDifficulty = :difficulty
          and e.createdAt >= :fromTime
          and e.createdAt <= :toTime
    """)
    long countByUserIdAndDifficultyBetween(Long userId, ContentDifficulty difficulty,
                                           LocalDateTime fromTime, LocalDateTime toTime);
}
