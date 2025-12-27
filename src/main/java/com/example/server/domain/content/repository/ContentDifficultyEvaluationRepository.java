package com.example.server.domain.content.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.server.domain.content.entity.ContentDifficultyEvaluation;
import com.example.server.domain.content.entity.vo.ContentDifficulty;

@Repository
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
