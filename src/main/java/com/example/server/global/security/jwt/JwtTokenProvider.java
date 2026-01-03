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

	private String createToken(String userId, String email, Long expirationMs) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationMs);

		return Jwts.builder()
			.setSubject(userId)
			.claim("email", email) // 이메일 추가!
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(getSigningKey())
			.compact();
	}

	public String generateToken(String userId, String email) {
		return createToken(userId, email, jwtPropertie.getExpiration());
	}

	public String generateRefreshToken(String userId, String email) {
		return createToken(userId, email, jwtPropertie.getRefreshExpiration());
	}

	public String getEmailFromToken(String token) {
		return parseClaims(token).get("email", String.class);
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
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = jwtPropertie.getSecret().getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
