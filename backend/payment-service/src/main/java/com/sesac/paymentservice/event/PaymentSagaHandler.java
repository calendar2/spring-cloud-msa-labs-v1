package com.sesac.paymentservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentSagaHandler {

	@RabbitListener(queues = "${order.event.queue.payment-request}")
	public void handlePaymentRequest(PaymentRequestEvent event) {
		log.info("결제 요청 이벤트 수신 - orderId: {}, amount: {}", event.getOrderId(), event.getTotalAmount());
	}
}