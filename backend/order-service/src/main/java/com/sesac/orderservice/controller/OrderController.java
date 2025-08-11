package com.sesac.orderservice.controller;

import java.util.List;

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
import jakarta.servlet.http.HttpServletRequest;
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

	@GetMapping("/my")
	@Operation(summary = "내 주문 목록", description = "로그인한 사용자의 주문 목록을 조회합니다")
	public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
		// API Gateway에서 전달한 X-User-Id 헤더에서 사용자 ID 추출
		String userIdHeader = request.getHeader("X-User-Id");
		if (userIdHeader == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		List<Order> orders = orderService.getOrdersByUserId(Long.parseLong(userIdHeader));
		return ResponseEntity.ok(orders);
	}
}
