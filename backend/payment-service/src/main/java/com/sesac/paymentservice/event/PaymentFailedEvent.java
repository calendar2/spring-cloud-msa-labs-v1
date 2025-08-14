package com.sesac.paymentservice.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFailedEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long orderId;
	private Long userId;
	private Long productId;
	private Integer quantity;
	private String reason;
}
