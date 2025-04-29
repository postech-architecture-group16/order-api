package com.fiap.challenge.order.infra.models.dto.request;

import java.util.List;

import com.fiap.challenge.order.application.domain.models.OrderProduct;

public record OrderRequestDTO(String customerId, List<OrderProduct> products) {
	
}
