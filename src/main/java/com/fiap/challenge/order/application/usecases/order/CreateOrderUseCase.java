package com.fiap.challenge.order.application.usecases.order;

import com.fiap.challenge.order.application.domain.models.Order;

public interface CreateOrderUseCase {

	Order createOrder(Order order);
	
	
}
