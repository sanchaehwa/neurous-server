package com.example.server.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "quiz")
@Builder
@Getter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "quiz_num")
    private int quizNum;

    @Column(name = "quiz_content")
    private String quizContent;

    @Column(name = "quiz_diff")
    private String quizDiff;

    @Column(name = "quiz_category")
    private String quizCategory;

    @Column(name = "content_id")
    private int contentId;
}
