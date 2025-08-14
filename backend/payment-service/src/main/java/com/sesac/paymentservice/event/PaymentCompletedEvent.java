package com.sesac.paymentservice.event;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long orderId;
	private Long userId;
	private BigDecimal amount;
}
