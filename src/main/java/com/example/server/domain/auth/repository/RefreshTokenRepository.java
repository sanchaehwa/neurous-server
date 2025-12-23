package com.example.server.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.domain.auth.entity.TokenManager;
import com.example.server.domain.user.entity.User;

public interface RefreshTokenRepository extends JpaRepository<TokenManager, Long>, RefreshTokenRepositoryCustom {

	Optional<TokenManager> findByUser(User user);

	Optional<TokenManager> findByToken(String token);
}
