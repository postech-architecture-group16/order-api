package com.fiap.challenge.order.application.domain.models;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;

public class Product {

	private UUID id;

	private String name;

	private CategorieEnums category;

	private BigDecimal price;

	private String description;


	public Product(UUID id, String name, CategorieEnums category, BigDecimal price, String description) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.description = description;
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

}