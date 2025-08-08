package com.sesac.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
	private Long productId;
	private Long userId;
	private Integer quantity;
}
