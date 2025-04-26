package com.fiap.challenge.order.infra.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.infra.models.dto.request.OrderRequestDTO;
import com.fiap.challenge.order.infra.models.dto.response.OrderResponseDTO;
import com.fiap.challenge.order.infra.service.OrderService;

@RestController
@RequestMapping("/order/api")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/create-order")
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
		Order order = orderService.createOrder(
				new Order(
				new Customer(UUID.fromString(orderRequestDTO.customerId())), 
				orderRequestDTO.products(), Boolean.FALSE));
		OrderResponseDTO orderResponse = new OrderResponseDTO(order);
		// @TODO enviar para criar um novo pagamento
		
		return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
	}
	
	@GetMapping("/find-order/{id}")
	public ResponseEntity<Order> findOrder(@PathVariable("id") UUID id){
		Order order = orderService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}
	
}
