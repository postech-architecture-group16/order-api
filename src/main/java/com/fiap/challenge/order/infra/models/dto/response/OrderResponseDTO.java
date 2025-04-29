package com.fiap.challenge.order.infra.models.dto.response;

import java.util.UUID;

import com.fiap.challenge.order.application.domain.models.Order;

public record OrderResponseDTO (UUID orderId, Long sequence){
	public OrderResponseDTO(Order order) {
		this(order.getId(), order.getOrderNumber());
	}
}
