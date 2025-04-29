package com.fiap.challenge.order.application.usecases.order;

import java.util.UUID;

import com.fiap.challenge.order.application.domain.models.Order;

public interface FindOrderUseCase {

	Order findById(UUID id);
}
