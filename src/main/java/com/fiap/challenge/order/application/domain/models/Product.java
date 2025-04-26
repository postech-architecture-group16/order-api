package com.fiap.challenge.order.application.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;

public class Product {

	private UUID id;

	private String name;

	private CategorieEnums category;

	private BigDecimal price;

	private String description;


	private final LocalDateTime createdAt;

	public Product(UUID id, String name, CategorieEnums category, BigDecimal price, String description,
                    LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
		this.createdAt = createdAt;
	}

	public Product(UUID id, String name, CategorieEnums category, BigDecimal price, String description) {
		this(id, name, category, price, description,  null);
	}

	public void update(Product product) {
		this.name = Optional.ofNullable(product).map(Product::getName).orElse(this.name);
		this.category = Optional.ofNullable(product).map(Product::getCategory).orElse(this.category);
		this.price = Optional.ofNullable(product).map(Product::getPrice).orElse(this.price);
		this.description = Optional.ofNullable(product).map(Product::getDescription).orElse(this.description);
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CategorieEnums getCategory() {
		return category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

}