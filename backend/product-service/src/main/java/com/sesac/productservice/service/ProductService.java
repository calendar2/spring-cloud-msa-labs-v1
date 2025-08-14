package com.sesac.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sesac.productservice.entity.Product;
import com.sesac.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {
	private final ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Product not found: " + id));
	}

	@Transactional
	public void decreaseStock(Long productId, Integer quantity) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new RuntimeException("Product not found: " + productId));

		if (product.getStockQuantity() <  quantity) {
			throw new RuntimeException("재고가 부족함");
		}

		product.setStockQuantity(product.getStockQuantity() - quantity);
		productRepository.save(product);

		log.info("재고 차감 완료");
	}
}
