package com.fiap.challenge.order.application.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

	private UUID id;
	
	private Customer customer;
	
	private BigDecimal total;
	
	private Long orderNumber;
	
	private LocalDateTime createAt;
	
	private LocalDateTime updateAt;
	
	private List<OrderProduct> products;
	
	private String paymentId;
	
	private Boolean isPaid;

	public Order(UUID id, Customer customer, BigDecimal total, Long orderNumber,LocalDateTime createAt,
			List<OrderProduct> products, Boolean isPaid) {
		this.id = id;
		this.customer = customer;
		this.total = total;
		this.orderNumber = orderNumber;
		this.createAt = createAt;
		this.products = products;
		this.isPaid = isPaid;
	}
	
	public Order(Customer customer, List<OrderProduct> products, Boolean isPaid) {
		this.customer = customer;
		this.products = products;
		this.isPaid = isPaid;
	}
	
	public UUID getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}
	
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	

}
