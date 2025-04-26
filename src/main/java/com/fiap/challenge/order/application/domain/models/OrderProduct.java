package com.fiap.challenge.order.application.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderProduct {

	private UUID id;
	
	private UUID orderId;
	
	private UUID productId;

	private BigDecimal price;
	
	private String productName;
	
	private LocalDateTime createdAt;

	public OrderProduct(UUID id, UUID orderId, UUID productId, BigDecimal price, String productName,
			LocalDateTime createdAt) {
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.price = price;
		this.productName = productName;
		this.createdAt = createdAt;
	}
	
	public OrderProduct() {
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
