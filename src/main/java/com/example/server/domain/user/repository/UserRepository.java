package com.example.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.domain.auth.enums.OAuthProvider;
import com.example.server.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(Long userId);

	Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);

}
