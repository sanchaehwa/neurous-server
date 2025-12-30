package com.example.server.domain.content.entity;

import com.example.server.domain.content.entity.vo.ContentCategory;
import com.example.server.domain.content.entity.vo.ContentLevel;

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
@AllArgsConstructor
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content_body", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_category")
	private ContentCategory contentCategory;

	@Enumerated(EnumType.STRING)
	@Column(name = "content_level", nullable = false)
	private ContentLevel contentLevel;

	@Column(name = "image_url")
	private String imageUrl;

	//조회수
	@Column(name = "hits", nullable = false)
	private int hits = 0; //조회수 초기값 0으로 설정
}

