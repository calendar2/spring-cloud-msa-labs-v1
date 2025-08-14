package com.sesac.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

	@Value("${order.event.exchange}")
	private String exchange;

	@Value("${order.event.queue.notification}")
	private String notificationQueue;

	@Value("${order.event.queue.inventory}")
	private String inventoryQueue;

	@Value("${order.event.queue.payment-request}")
	private String paymentRequestQueue;

	@Value("${order.event.queue.inventory-failed}")
	private String inventoryFailedQueue;

	@Value("${order.event.queue.payment-completed}")
	private String paymentCompletedQueue;

	@Value("${order.event.queue.payment-failed}")
	private String paymentFailedQueue;

	@Value("${order.event.queue.inventory-restore}")
	private String inventoryRestoreQueue;

	@Value("${order.event.routing-key.notification}")
	private String notificationRoutingKey;

	@Value("${order.event.routing-key.inventory}")
	private String inventoryRoutingKey;

	@Value("${order.event.routing-key.payment-request}")
	private String paymentRequestRoutingKey;

	@Value("${order.event.routing-key.inventory-failed}")
	private String inventoryFailedRoutingKey;

	@Value("${order.event.routing-key.payment-completed}")
	private String paymentCompletedRoutingKey;

	@Value("${order.event.routing-key.payment-failed}")
	private String paymentFailedRoutingKey;

	@Value("${order.event.routing-key.inventory-restore}")
	private String inventoryRestoreRoutingKey;

	// Exchange 정의
	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(exchange);
	}

	// Queue 정의
	@Bean
	public Queue notificationQueue() {
		return QueueBuilder.durable(notificationQueue).build();
	}

	@Bean
	public Queue inventoryQueue() {
		return QueueBuilder.durable(inventoryQueue).build();
	}

	@Bean
	public Queue paymentRequestQueue() { return QueueBuilder.durable(paymentRequestQueue).build(); }

	@Bean
	public Queue inventoryFailedQueue() { return QueueBuilder.durable(inventoryFailedQueue).build(); }

	@Bean
	public Queue paymentCompletedQueue() { return QueueBuilder.durable(paymentCompletedQueue).build(); }

	@Bean
	public Queue paymentFailedQueue() { return QueueBuilder.durable(paymentFailedQueue).build(); }

	@Bean
	public Queue inventoryRestoreQueue() { return QueueBuilder.durable(inventoryRestoreQueue).build(); }

	// Binding 정의
	@Bean
	public Binding notificationBinding() {
		return BindingBuilder.bind(notificationQueue())
			.to(orderExchange())
			.with(notificationRoutingKey);
	}

	@Bean
	public Binding inventoryBinding() {
		return BindingBuilder.bind(inventoryQueue())
			.to(orderExchange())
			.with(inventoryRoutingKey);
	}

	@Bean
	public Binding paymentRequestBinding() {
		return BindingBuilder.bind(paymentRequestQueue())
			.to(orderExchange())
			.with(paymentRequestRoutingKey);
	}

	@Bean
	public Binding inventoryFailedBinding() {
		return BindingBuilder.bind(inventoryFailedQueue())
			.to(orderExchange())
			.with(inventoryFailedRoutingKey);
	}

	@Bean
	public Binding paymentCompletedBinding() {
		return BindingBuilder.bind(paymentCompletedQueue())
			.to(orderExchange())
			.with(paymentCompletedRoutingKey);
	}

	@Bean
	public Binding paymentFailedBinding() {
		return BindingBuilder.bind(paymentFailedQueue())
			.to(orderExchange())
			.with(paymentFailedRoutingKey);
	}

	@Bean
	public Binding inventoryRestoreBinding() {
		return BindingBuilder.bind(inventoryRestoreQueue())
			.to(orderExchange())
			.with(inventoryRestoreRoutingKey);
	}

	// JSON 메시지 컨버터 추가
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}