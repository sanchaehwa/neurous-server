package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterest, Integer> {

    @Query("""
           select i.interestName
           from UserInterest ui
           join Interest i on ui.interestId = i.interestId
           where ui.userId = :userId
           order by ui.priority asc
           """)
    List<String> findInterestNamesByUserIdOrderByPriorityAsc(int userId);
}
