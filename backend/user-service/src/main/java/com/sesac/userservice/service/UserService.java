package com.sesac.userservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.userservice.dto.LoginRequest;
import com.sesac.userservice.dto.LoginResponse;
import com.sesac.userservice.entity.User;
import com.sesac.userservice.repository.UserRepository;
import com.sesac.userservice.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("User not found: " + id));
	}

	public LoginResponse login(LoginRequest request) {
		// 1. 이메일로 사용자 조회
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new RuntimeException("User not found: " + request.getEmail()));

		// 2. 패스워드 검증
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Incorrect password");
		}

		// 3. JWT 토큰 생성
		String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId());

		// 4. 응답
		return new LoginResponse(token, user.getId(), user.getEmail(), user.getName());
	}
}
