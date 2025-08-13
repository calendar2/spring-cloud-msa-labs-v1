package com.sesac.orderservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.orderservice.client.ProductServiceClient;
import com.sesac.orderservice.client.dto.ProductDto;
import com.sesac.orderservice.client.dto.UserDto;
import com.sesac.orderservice.dto.OrderRequestDto;
import com.sesac.orderservice.entity.Order;
import com.sesac.orderservice.event.OrderCreatedEvent;
import com.sesac.orderservice.event.OrderEventPublisher;
import com.sesac.orderservice.facade.UserServiceFacade;
import com.sesac.orderservice.repository.OrderRepository;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserServiceFacade userServiceFacade;
	private final ProductServiceClient productServiceClient;
	private final Tracer tracer;
	private final OrderEventPublisher orderEventPublisher;

	public Order findById(Long id) {
		return orderRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Order not found: " + id));
	}

	// 주문 생성 (고객이 주문했을 때)
	@Transactional
	public Order createOrder(OrderRequestDto request) {
		Span span = tracer.nextSpan()
			.name("createOrder")
			.tag("order.userId", request.getUserId())
			.tag("order.productId", request.getProductId())
			.start();

		try(Tracer.SpanInScope ws = tracer.withSpan(span)) {
			UserDto user = userServiceFacade.getUserWithFallback(request.getUserId());
			if (user == null) {
				throw new RuntimeException("User not found: " + request.getUserId());
			}

			ProductDto product = productServiceClient.getProductById(request.getProductId());
			if (product == null) {
				throw new RuntimeException("Product not found: " + request.getProductId());
			}

			// if (product.getStockQuantity() < request.getQuantity()) {
			// 	throw new RuntimeException("재고 부족");
			// }

			Order order = new Order();
			order.setUserId(user.getId());
			order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
			order.setStatus("COMPLETED");

			// 비동기 이벤트 발행
			OrderCreatedEvent event = new OrderCreatedEvent(
				order.getId(),
				user.getId(),
				product.getId(),
				request.getQuantity(),
				order.getTotalAmount(),
				LocalDateTime.now()
			);
			orderEventPublisher.publishOrderCreated(event);

			return orderRepository.save(order);
		} catch (Exception e) {
			span.tag("error", e.getMessage());
			throw e;
		} finally {
			span.end();
		}
	}

	public List<Order> getOrdersByUserId(Long userId) {
		return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
	}
}
