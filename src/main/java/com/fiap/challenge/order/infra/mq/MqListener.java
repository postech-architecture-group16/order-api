package com.fiap.challenge.order.infra.mq;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.order.infra.models.dto.response.PaymentResponseDTO;
import com.fiap.challenge.order.infra.service.OrderService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MqListener {

	private OrderService orderService;
	
	private ObjectMapper objectMapper;
	
	public MqListener(OrderService orderService) {
		this.orderService = orderService;
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	@SqsListener("${queue.name.consumer}")
	public void receive(@Payload String message) throws  JsonProcessingException {
		PaymentResponseDTO paymentResponse = objectMapper.readValue(message, PaymentResponseDTO.class);
		String paymentId = String.valueOf(paymentResponse.paymentId());
		orderService.confirmPayment(paymentResponse.orderId(), 
				paymentId, 
				paymentResponse.isPaid(), 
				paymentResponse.orderNumber());
		log.info("Payment received: {}", paymentResponse.orderId());
	}
	
	
}
