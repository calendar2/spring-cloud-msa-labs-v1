package com.sesac.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sesac.orderservice.client.dto.ProductDto;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
	@GetMapping("/products/{id}")
	ProductDto getProductById(@PathVariable Long id);
}
