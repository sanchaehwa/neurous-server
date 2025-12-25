package com.example.server.domain.content.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "difficulty_basetime")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DifficultyBasetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "base_time_id")
    private int baseTimeId;

    @Column(name = "base_time", nullable = false)
    private LocalDateTime baseTime;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public DifficultyBasetime(Long userId, LocalDateTime baseTime) {
        this.userId = userId;
        this.baseTime = baseTime;
    }

    public static DifficultyBasetime now(Long userId) {
        return new DifficultyBasetime(userId, LocalDateTime.now());
    }

    public void reset(LocalDateTime newBaseTime) {
        this.baseTime = newBaseTime;
    }

    public void resetNow() {
        this.baseTime = LocalDateTime.now();
    }
}
