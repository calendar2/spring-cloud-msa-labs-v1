package com.sesac.orderservice.facade;

import org.springframework.stereotype.Service;

import com.sesac.orderservice.client.UserServiceClient;
import com.sesac.orderservice.client.dto.UserDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceFacade {
	private final UserServiceClient userServiceClient;

	@CircuitBreaker(name = "user-service", fallbackMethod = "getUserFallback")
	@Retry(name = "user-service")
	public UserDto getUserWithFallback(Long userId) {
		log.info("User service 호출 시도 - userId = {}", userId);
		return userServiceClient.getUserById(userId);
	}

	// Fallback 메서드
	public UserDto getUserFallback(Long userId, Throwable ex) {
		log.warn("User service 장애 감지! Fallback 실행 - userId = {}, 에러: {}", userId, ex.getMessage());

		UserDto defaultUser = new UserDto();
		defaultUser.setId(userId);
		defaultUser.setName("임시 사용자");
		defaultUser.setEmail("temp@example.com");

		return defaultUser;
	}
}
