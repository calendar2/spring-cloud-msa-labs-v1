package com.sesac.orderservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.orderservice.client.ProductServiceClient;
import com.sesac.orderservice.client.UserServiceClient;
import com.sesac.orderservice.client.dto.ProductDto;
import com.sesac.orderservice.client.dto.UserDto;
import com.sesac.orderservice.dto.OrderRequestDto;
import com.sesac.orderservice.entity.Order;
import com.sesac.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserServiceClient userServiceClient;
	private final ProductServiceClient productServiceClient;

	public Order findById(Long id) {
		return orderRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Order not found: " + id));
	}

	// 주문 생성 (고객이 주문했을 때)
	@Transactional
	public Order createOrder(OrderRequestDto request) {
		UserDto user = userServiceClient.getUserById(request.getUserId());
		if (user == null) {
			throw new RuntimeException("User not found: " + request.getUserId());
		}

		ProductDto product = productServiceClient.getProductById(request.getProductId());
		if (product == null) {
			throw new RuntimeException("Product not found: " + request.getProductId());
		}

		if (product.getStockQuantity() < request.getQuantity()) {
			throw new RuntimeException("재고 부족");
		}

		Order order = new Order();
		order.setUserId(user.getId());
		order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
		order.setStatus("COMPLETED");

		return orderRepository.save(order);
	}

	public List<Order> getOrdersByUserId(Long userId) {
		return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
	}
}
