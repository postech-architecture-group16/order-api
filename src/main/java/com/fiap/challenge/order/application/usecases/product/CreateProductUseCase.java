package com.fiap.challenge.order.application.usecases.product;

import com.fiap.challenge.order.application.domain.models.Product;

public interface CreateProductUseCase {
	
	void create(Product product);
}
