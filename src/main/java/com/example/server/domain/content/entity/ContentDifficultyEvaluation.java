package com.example.server.domain.content.entity;

import com.example.server.domain.content.entity.vo.ContentDifficulty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "content_difficulty_evaluation")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContentDifficultyEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_difficulty_evaluation_id")
    private int ContentDifficultyEvaluationId;

    @Column(name = "content_id")
    private int contentId;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_difficulty")
    private ContentDifficulty contentDifficulty;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
