package com.fiap.challenge.order.infra.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.order.application.domain.models.Order;

@Component
public class MqProducerProduction {

    private final Queue productionQueue;

	private RabbitTemplate rabbitTemplate;
	
	public MqProducerProduction(@Autowired RabbitTemplate rabbitTemplate, @Qualifier("productionQueue") Queue productionQueue) {
		this.rabbitTemplate = rabbitTemplate;
		this.productionQueue = productionQueue;
	}
	
	public void send(Order order) throws JsonProcessingException, AmqpException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		rabbitTemplate.convertAndSend(this.productionQueue.getName(), writer.writeValueAsString(order));
	}
	
	
}
