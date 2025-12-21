package com.example.server.domain.content.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_interest")
public class UserInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_interest_id")
    private int useInterestId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "interest_id")
    private Integer interestId;

    @Column(name = "priority", nullable = false)
    private Integer priority;
}
