package com.example.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.domain.content.entity.vo.ContentLevel;
import com.example.server.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(Long userId);

	Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);

	@Query("select u.level from User u where u.id = :userId")
	Optional<ContentLevel> findLevelByUserId(Long userId);

}
