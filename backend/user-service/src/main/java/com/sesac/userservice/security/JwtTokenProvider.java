package com.sesac.userservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret:mySecretKeyForJWTTokenGenerationThatShouldBeLongEnough}")
	private String jwtSecret;

	@Value("${jwt.expiration:86400000}") // 24시간
	private int jwtExpirationMs;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}

	public String generateToken(String email, Long userId) {
		Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

		return Jwts.builder()
			.subject(email)
			.claim("userId", userId)
			.issuedAt(new Date())
			.expiration(expiryDate)
			.signWith(getSigningKey())
			.compact();
	}

	public String getEmailFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return claims.getSubject();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return claims.get("userId", Long.class);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}