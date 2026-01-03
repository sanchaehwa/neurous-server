package com.example.server.domain.content.entity;

import java.time.LocalDateTime;

import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Content extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "content_body", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "content_date", nullable = false)
	private LocalDateTime contentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_category", nullable = false)
	private ContentCategory contentCategory;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_level", nullable = false)
	private ContentLevel contentLevel;

	@Column(name = "image_url", length = 500)
	private String imageUrl;

	@Column(name = "batch_time", nullable = false)
	private LocalDateTime batchTime;
	
	@Builder.Default
	@Column(name = "hits", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int hits = 0;

}
