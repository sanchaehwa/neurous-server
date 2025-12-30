package com.example.server.domain.content.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.content.entity.vo.ContentLevel;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	// 1. 카테고리와 난이도로 목록 조회 (페이징 포함)
	List<Content> findByContentLevelAndContentCategory(ContentLevel contentLevel, ContentCategory contentCategory,
		Pageable pageable);

	// 2. 특정 난이도 전체를 최신순으로 조회
	List<Content> findByContentLevelOrderByContentIdDesc(ContentLevel contentLevel, Pageable pageable);

	// 3. 특정 사용자가 읽지 않은 컨텐츠 중 하나를 랜덤 추출
	@Query(value = """
		SELECT *
		FROM content c
		WHERE c.content_level = :contentLevel
		  AND c.content_category = :category
		  AND NOT EXISTS (
		                    SELECT 1
		                    FROM read_content rc
		                    WHERE rc.user_id = :userId
		                      AND rc.content_id = c.content_id
		                )
		ORDER BY RAND()
		LIMIT 1
		""", nativeQuery = true)
	Optional<Content> findRandomUnreadByContentLevelAndCategory(
		@Param("userId") Long userId,
		@Param("contentLevel") String contentLevel,
		@Param("category") String category
	);

	// 4. 특정 사용자가 읽지 않았으면서, 현재 화면의 리스트(excludedIds)를 제외하고 랜덤 추출
	@Query(value = """
		SELECT *
		FROM content c
		WHERE c.content_level = :contentLevel
		  AND c.content_category = :category
		  AND c.content_id NOT IN (:excludedIds)
		  AND NOT EXISTS (
		                    SELECT 1
		                    FROM read_content rc
		                    WHERE rc.user_id = :userId
		                      AND rc.content_id = c.content_id
		                )
		ORDER BY RAND()
		LIMIT 1
		""", nativeQuery = true)
	Optional<Content> findRandomUnreadByContentLevelAndCategoryExcludeIds(
		@Param("userId") Long userId,
		@Param("contentLevel") String contentLevel,
		@Param("category") String category,
		@Param("excludedIds") List<Long> excludedIds
	);

	@Query("""
		    select c from Content c 
		    where c.contentLevel = :contentLevel 
		      and lower(c.title) like lower(concat('%', :keyword, '%'))
		    order by c.contentId desc
		""")
	List<Content> searchByTitle(
		@Param("contentLevel") ContentLevel contentLevel,
		@Param("keyword") String keyword,
		Pageable pageable
	);
}
