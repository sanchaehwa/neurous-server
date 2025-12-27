package com.example.server.domain.auth.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepositoryCustom {

	int deleteAllByExpiresAtBefore(LocalDateTime now);
}
