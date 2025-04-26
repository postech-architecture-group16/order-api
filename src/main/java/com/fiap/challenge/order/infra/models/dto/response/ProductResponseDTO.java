package com.fiap.challenge.order.infra.models.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;

public record ProductResponseDTO( UUID id,
								String name,
								 CategorieEnums category,  
								 BigDecimal price,
								 String description) {
	public ProductResponseDTO(Product product) {
		this(product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.getDescription());
	}
}