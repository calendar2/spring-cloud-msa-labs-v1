package com.sesac.orderservice.client.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDto {
	private Long id;
	private String name;
	private BigDecimal price;
	private Integer stockQuantity;
}
