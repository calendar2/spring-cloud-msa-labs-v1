package com.sesac.paymentservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.paymentservice.entity.Payment;
import com.sesac.paymentservice.entity.PaymentStatus;
import com.sesac.paymentservice.event.PaymentRequestEvent;
import com.sesac.paymentservice.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
	private final PaymentRepository paymentRepository;

	// 결제 로직
	@Transactional
	public Payment processPayment(PaymentRequestEvent event) {
		// 성공, 실패
		Payment payment = new Payment();
		payment.setOrderId(event.getOrderId());
		payment.setUserId(event.getUserId());
		payment.setAmount(event.getTotalAmount());
		payment.setStatus(PaymentStatus.PENDING);
		payment.setPaymentMethod("CARD");

		Payment savedPayment = paymentRepository.save(payment);

		// 결제 처리
		try {
			Thread.sleep(2000);

			if (Math.random() < 0.3) {
				throw new RuntimeException("잔액 부족");
			}
			savedPayment.setStatus(PaymentStatus.COMPLETED);
			paymentRepository.save(savedPayment);
		} catch (Exception e) {
			savedPayment.setStatus(PaymentStatus.FAILED);
			savedPayment.setFailureReason(e.getMessage());
			paymentRepository.save(savedPayment);
			throw new RuntimeException("결제 실패");
		}

		return savedPayment;
	}
}
