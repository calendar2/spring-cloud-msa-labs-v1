package com.sesac.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/config")
@RefreshScope
@Tag(name = "Config Test", description = "설정값 확인용 API")
public class ConfigController {
	@Value("${user.service.name}")
	private String serviceName;

	@Value("${user.service.version}")
	private String serviceVersion;

	@Value("${user.service.description}")
	private String serviceDescription;

	@GetMapping
	@Operation(summary = "설정값 조회", description = "Config Server에서 가져온 설정값을 확인")
	public Map<String, String> getConfig() {
		Map<String, String> config = new HashMap<>();
		config.put("serviceName", serviceName);
		config.put("serviceVersion", serviceVersion);
		config.put("serviceDescription", serviceDescription);
		return config;
	}
}
