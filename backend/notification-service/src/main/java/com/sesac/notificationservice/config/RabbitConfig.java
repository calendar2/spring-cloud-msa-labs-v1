package com.sesac.notificationservice.config;

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

	@Value("${order.event.routing-key.notification}")
	private String notificationRoutingKey;

	@Value("${order.event.routing-key.inventory}")
	private String inventoryRoutingKey;

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

	// JSON 메시지 컨버터 추가
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}