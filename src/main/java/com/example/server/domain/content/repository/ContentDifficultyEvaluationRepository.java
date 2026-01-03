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

	@Query("""
		   SELECT e FROM ContentDifficultyEvaluation e 
		   JOIN e.readContent rc 
		   WHERE rc.user.id = :userId AND rc.content.contentId = :contentId
		""")
	Optional<ContentDifficultyEvaluation> findEvaluation(@Param("userId") Long userId,
		@Param("contentId") Long contentId);

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

	//ReadContent ID를 통해 이미 평가가 존재했는지 확인
	boolean existsByReadContent_ReadContentId(Long readContentId);

	//기존 시간 이후로 이 유저가 평가한 전체 횟수
	@Query("""
		SELECT COUNT(e) FROM ContentDifficultyEvaluation e 
		WHERE e.readContent.user.id = :userId AND e.createdAt >= :baseTime""")
	long countByUserIdAfter(@Param("userId") Long userId, @Param("baseTime") LocalDateTime baseTime);

	//기존 시간 이후로 특정 난이도 선택한 횟수
	@Query("""
		SELECT COUNT(e) FROM ContentDifficultyEvaluation e
		WHERE e.readContent.user.id = :userId
		AND e.contentDifficulty = :difficulty
		AND e.createdAt >= :baseTime """)
	long countByUserIdAndDifficultyAfter(
		@Param("userId") Long userId,
		@Param("difficulty") ContentDifficulty difficulty,
		@Param("baseTime") LocalDateTime baseTime
	);
}

