package com.fiap.challenge.order.infra.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqProducer {

    private final Queue queue;

	private RabbitTemplate rabbitTemplate;

	public MqProducer(@Autowired RabbitTemplate rabbitTemplate, Queue queue) {
		this.rabbitTemplate = rabbitTemplate;
		this.queue = queue;
	}
	
	public void send(String order) {
		rabbitTemplate.convertAndSend(this.queue.getName(), order);
	}
	
	
}
