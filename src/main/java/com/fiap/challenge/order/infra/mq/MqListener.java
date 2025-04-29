package com.fiap.challenge.order.infra.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class MqListener {

	private ObjectMapper objectMapper;
	
	public MqListener() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	@RabbitListener(queues = {"${queue.name.consumer}"})
	public void receive(@Payload String message) {
		System.out.println(message);
	}
	
	
}
