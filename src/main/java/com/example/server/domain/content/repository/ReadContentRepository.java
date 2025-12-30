package com.example.server.domain.content.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.server.domain.content.entity.Content;
import com.example.server.domain.content.entity.ReadContent;

@Repository
public interface ReadContentRepository extends JpaRepository<ReadContent, Long> {
	
	@Query("SELECT rc.content FROM ReadContent rc " +
		"WHERE rc.user.id = :userId " +
		"ORDER BY rc.readContentId DESC")
	List<Content> findReadContentsByUserId(@Param("userId") Long userId, Pageable pageable);

	Optional<ReadContent> findByUser_IdAndContent_ContentId(Long userId, Long contentId);
}
