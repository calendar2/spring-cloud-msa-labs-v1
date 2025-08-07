package com.sesac.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.productservice.entity.Product;
import com.sesac.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping
	@Operation(summary = "상품 목록 조회", description = "모든 상품 정보를 조회합니다")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok().body(productService.findAll());
	}

	@GetMapping("/{id}")
	@Operation(summary = "단일 상품 조회", description = "ID로 상품 하나의 정보를 조회합니다")
	public ResponseEntity<Product> getUser(@PathVariable Long id){
		try {
			Product product = productService.findById(id);
			return ResponseEntity.ok(product);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
