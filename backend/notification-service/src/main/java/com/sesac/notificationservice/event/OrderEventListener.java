package com.sesac.notificationservice.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
	@RabbitListener(queues = "${order.event.queue.notification}")
	public void handleOrderEvent(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - orderId: {}", event.getOrderId());

		try {
			Thread.sleep(3000);
			log.info("이메일 발송 완료 - orderId: {}", event.getOrderId());
		} catch (InterruptedException e) {
			log.error("이메일 발송 실패 - orderId: {}", event.getOrderId());
		}
	}
}
