package com.sesac.orderservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.orderservice.entity.Order;
import com.sesac.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;

	public Order findById(Long id) {
		return orderRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Order not found: " + id));
	}
}
