package com.example.server.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "quiz_choice")
@Getter
public class QuizChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_choice_id")
    private int quizChoiceId;

    @Column(name = "choice_no")
    private int choiceNo;

    @Column(name = "choice_text")
    private String choiceText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "quiz_id")
    private int quizId;
}
