package com.fiap.challenge.order.infra.controller;

import java.util.Objects;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.infra.models.dto.request.OrderRequestDTO;
import com.fiap.challenge.order.infra.models.dto.response.OrderResponseDTO;
import com.fiap.challenge.order.infra.mq.MqProducer;
import com.fiap.challenge.order.infra.service.OrderService;

@RestController
@RequestMapping("/order/api")
public class OrderController {

	private OrderService orderService;
	private MqProducer mqProducer;
	
	public OrderController(OrderService orderService, MqProducer mqProducer) {
		this.orderService = orderService;
		this.mqProducer = mqProducer;
	}
	
	@PostMapping("/create-order")
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws JsonProcessingException, AmqpException{
		Customer customer = null;
		if(Objects.nonNull(orderRequestDTO.customerId())) {
			customer = new Customer(UUID.fromString(orderRequestDTO.customerId()));
		}
		Order order = orderService.createOrder(
				new Order(
						customer, 
				orderRequestDTO.products(), Boolean.FALSE));
		OrderResponseDTO orderResponse = new OrderResponseDTO(order);
		mqProducer.send(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
	}
	
	@GetMapping("/find-order/{id}")
	public ResponseEntity<Order> findOrder(@PathVariable("id") UUID id){
		Order order = orderService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}
	
}
