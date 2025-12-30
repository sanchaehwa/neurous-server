package com.example.server.domain.content.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.server.domain.content.entity.ContentDifficultyEvaluation;
import com.example.server.domain.content.entity.vo.ContentDifficulty;

@Repository
public interface ContentDifficultyEvaluationRepository extends JpaRepository<ContentDifficultyEvaluation, Long> {

	Optional<ContentDifficultyEvaluation> findByReadContent_User_IdAndReadContent_Content_ContentId(Long userId,
		Long contentId);

	@Query("""
		    select count(e)
		    from ContentDifficultyEvaluation e
		    where e.readContent.user.id = :userId
		      and e.contentDifficulty = :difficulty
		      and e.createdAt between :fromTime and :toTime
		""")
	long countByUserIdAndDifficultyBetween(
		@Param("userId") Long userId,
		@Param("difficulty") ContentDifficulty difficulty,
		@Param("fromTime") LocalDateTime fromTime,
		@Param("toTime") LocalDateTime toTime
	);
}
