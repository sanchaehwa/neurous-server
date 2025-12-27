package com.example.server.domain.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.server.domain.user.entity.vo.UserField;
import com.example.server.domain.user.entity.vo.UserInterest;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

	@Query("""
		select ui.interest
		from UserInterest ui
		where ui.user.id = :userId
		order by ui.priority asc
		""")
	List<UserField> findInterestsByUserIdOrderByPriorityAsc(Long userId);
}
