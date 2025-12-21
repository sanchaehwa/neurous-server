package com.example.server.domain.content.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "read_content")
public class ReadContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "read_content_id")
    private int readContentId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "content_id")
    private int contentId;


}
