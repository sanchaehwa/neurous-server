package com.example.server.User.domain.entity;


import com.example.server.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    private String displayName;
    private String email;
    private String provider;     // google / kakao / naver
    private String providerId;
    private String imageFilename;
    private String imageUrl;
    private Boolean isActive;
    private LocalDateTime nameUpdateAt; // displayName 변경 시 업데이트

}