package com.sesac.userservice.dto;

import lombok.Data;

@Data
public class LoginResponse {
	private String token;
	private String type = "Bearer";
	private Long userId;
	private String email;
	private String name;

	public LoginResponse(String token, Long userId, String email, String name) {
		this.token = token;
		this.userId = userId;
		this.email = email;
		this.name = name;
	}
}
