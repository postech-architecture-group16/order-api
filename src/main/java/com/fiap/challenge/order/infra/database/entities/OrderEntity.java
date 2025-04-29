package com.fiap.challenge.order.infra.database.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "customer_id")
	private UUID customerId;
	
	@Column(nullable = false)
	private BigDecimal total;
	
	@Column(name = "order_number")
	private Long orderNumber;
	
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "products", columnDefinition = "json")
	private List<OrderProduct> products;
	
	@Column(name = "payment_id")
	private String paymentId;
	
	@Column(nullable = false)
	private Boolean isPaid;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	
	public OrderEntity(UUID id, 
			UUID customerId, 
			BigDecimal total, 
			List<OrderProduct> products,
			String paymentId,
			Boolean isPaid, LocalDateTime createdAt) {
		this.id = id;
		this.customerId = customerId;
		this.total = total;
		this.products = products;
		this.paymentId = paymentId;
		this.isPaid = isPaid;
		this.createdAt = createdAt;
	}
	
	public OrderEntity(Order order) {
		this.id = order.getId();
		this.customerId = Objects.nonNull(order.getCustomer()) ? order.getCustomer().getId() : null;
		this.total = order.getTotal();
		this.orderNumber = order.getOrderNumber();
		this.products = order.getProducts();
		this.isPaid = order.getIsPaid();
		this.createdAt = order.getCreateAt();
		this.updatedAt = order.getUpdateAt();
	}
	
	public Order toOrder() {
		return new Order(id, null, total, orderNumber, createdAt, products, paymentId, isPaid);
	}
	public Order toOrder(Customer customer) {
		return new Order(id, customer, total, orderNumber, createdAt, products, paymentId, isPaid);
	}
}
