package com.example.server.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private final JwtProperties jwtPropertie;

	public String generateToken(String userId) {
		return createToken(userId, jwtPropertie.getExpiration());
	}

	public String generateRefreshToken(String userId) {
		return createToken(userId, jwtPropertie.getRefreshExpiration());
	}

	private String createToken(String userId, Long expirationMs) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationMs);

		return Jwts.builder()
			.subject(userId)
			.issuedAt(now)
			.expiration(expiryDate)
			.signWith(getSigningKey())
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		return Long.valueOf(parseClaims(token).getSubject());
	}

	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (Exception e) {
			log.debug("Invalid JWT token: {}", e.getMessage());
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = jwtPropertie.getSecret().getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
