package com.sesac.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.userservice.dto.LoginRequest;
import com.sesac.userservice.dto.LoginResponse;
import com.sesac.userservice.entity.User;
import com.sesac.userservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/{id}")
	@Operation(summary = "사용자 조회", description = "ID로 사용자 정보를 조회합니다")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		try {
			User user = userService.findById(id);
			return ResponseEntity.ok(user);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/login")
	@Operation(summary = "로그인", description = "이메일과 패스워드로 로그인하고 JWT 토큰을 발급받습니다")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		try {
			LoginResponse response = userService.login(request);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
