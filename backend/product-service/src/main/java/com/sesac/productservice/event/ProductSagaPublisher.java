package com.sesac.productservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSagaPublisher {
	private final RabbitTemplate rabbitTemplate;

	@Value("${order.event.exchange}")
	private String exchange;

	@Value("${order.event.routing-key.payment-request}")
	private String paymentRequestRoutingKey;

	@Value("${order.event.routing-key.inventory-failed}")
	private String inventoryFailedRoutingKey;

	// 재고 차감 성공 시 -> 결제 요청 이벤트 발행
	public void publishPaymentRequest(PaymentRequestEvent event) {
		log.info("결제 요청 이벤트 발행 - orderId: {}, amount: {}", event.getOrderId(), event.getTotlaAmount());
		rabbitTemplate.convertAndSend(exchange, paymentRequestRoutingKey, event);
		log.info("결제 요청 이벤트 발행 완료");
	}

	// 재고 부족 시 -> 재고 실패 이벤트 발행
	public void publishInventoryFailed(InventoryFailedEvent event) {
		log.info("재고 실패 이벤트 발행 - orderId: {}, reason: {}", event.getOrderId(), event.getReason());
		rabbitTemplate.convertAndSend(exchange, inventoryFailedRoutingKey, event);
		log.info("재고 실패 이벤트 발행 완료");
	}

}
