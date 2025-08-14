package com.sesac.productservice.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.sesac.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
	private final ProductService productService;

	@RabbitListener(queues = "${order.event.queue.inventory}")
	public void handleOrderEvent(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - orderId: {}", event.getOrderId());

		try {
			productService.decreaseStock(event.getProductId(), event.getQuantity());
		} catch (Exception e) {
			log.error("재고 차감 실패 - productId: {}", event.getProductId());
		}
	}
}
