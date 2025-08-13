package com.sesac.orderservice.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {
	private final RabbitTemplate rabbitTemplate;

	@Value("${order.event.exchange}")
	private String exchange;

	@Value("${order.event.routing-key.notification}")
	private String notificationRoutingKey;

	@Value("${order.event.routing-key.inventory}")
	private String inventoryRoutingKey;

	public void publishOrderCreated(OrderCreatedEvent event) {
		log.info("주문 완료 이벤트 발행 - orderId: {}", event.getOrderId());

		// 알림 서비스에 이벤트 전송
		rabbitTemplate.convertAndSend(exchange, notificationRoutingKey, event);
		log.info("알림 서비스에 이벤트 발행 완료");

		// 상품 서비스에 이벤트 전송
		rabbitTemplate.convertAndSend(exchange, inventoryRoutingKey, event);
		log.info("상품 서비스에 이벤트 발행 완료");
	}
}
