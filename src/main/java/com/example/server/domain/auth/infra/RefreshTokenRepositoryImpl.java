package com.example.server.domain.auth.infra;

import static com.example.server.domain.auth.entity.QTokenManager.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.example.server.domain.auth.repository.RefreshTokenRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public int deleteAllByExpiresAtBefore(LocalDateTime now) {
		long deletedCount = queryFactory
			.delete(tokenManager)
			.where(tokenManager.expireAt.lt(now))
			.execute();

		return (int)deletedCount;
	}
}
