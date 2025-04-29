package com.fiap.challenge.order.infra.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.order.infra.models.dto.response.PaymentResponseDTO;
import com.fiap.challenge.order.infra.service.OrderService;

@Component
public class MqListener {

	private OrderService orderService;
	
	private ObjectMapper objectMapper;
	
	public MqListener(OrderService orderService) {
		this.orderService = orderService;
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	@RabbitListener(queues = {"${queue.name.consumer}"})
	public void receive(@Payload String message) throws  JsonProcessingException {
		PaymentResponseDTO paymentResponse = objectMapper.readValue(message, PaymentResponseDTO.class);
		
		orderService.confirmPayment(paymentResponse.orderId(), 
				String.valueOf(paymentResponse.paymentId()), 
				paymentResponse.isPaid(), 
				paymentResponse.orderNumber());
	}
	
	
}
