package com.sesac.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesac.orderservice.dto.OrderRequestDto;
import com.sesac.orderservice.entity.Order;
import com.sesac.orderservice.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@GetMapping("/{id}")
	@Operation(summary = "주문 조회", description = "ID로 주문 정보를 조회합니다")
	public ResponseEntity<Order> getUser(@PathVariable Long id){
		try {
			Order order = orderService.findById(id);
			return ResponseEntity.ok(order);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto request) {
		try {
			Order order = orderService.createOrder(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(order);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
