package com.sesac.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sesac.orderservice.client.dto.UserDto;

@FeignClient(name = "user-service")
public interface UserServiceClient {
	@GetMapping("/users/{id}")
	UserDto getUserById(@PathVariable Long id);
}
