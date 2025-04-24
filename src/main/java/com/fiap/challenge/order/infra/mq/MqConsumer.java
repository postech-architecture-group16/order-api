package com.fiap.challenge.order.infra.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MqConsumer {

	@RabbitListener(queues = {"${queue.name.consumer}"})
	public void receive(@Payload String message) {
		System.out.println(message);
	}
	
	
}
