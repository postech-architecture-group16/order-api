package com.fiap.challenge.order.infra.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.order.application.domain.models.Order;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Component
public class MqProducer {

    private final Queue queue;

	private SqsTemplate sqsTemplate;
	
	public MqProducer(@Autowired SqsTemplate sqsTemplate, Queue queue) {
		this.sqsTemplate = sqsTemplate;
		this.queue = queue;
	}
	
	public void send(Order order) throws JsonProcessingException, AmqpException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		sqsTemplate.send(this.queue.getName(), writer.writeValueAsString(order));
	}
	
	
}
