package com.fiap.challenge.order.infra.database.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;
import com.fiap.challenge.order.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.order.infra.models.dto.response.OrderResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
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
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum status;
	
	@Column(nullable = false)
	private Boolean isPaid;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	
	public OrderEntity(){}
	
	public OrderEntity(UUID id, UUID customerId, BigDecimal total, Long orderNumber, List<OrderProduct> products,
			OrderStatusEnum status, Boolean isPaid, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.customerId = customerId;
		this.total = total;
		this.orderNumber = orderNumber;
		this.products = products;
		this.status = status;
		this.isPaid = isPaid;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public OrderEntity(Order order) {
		this.id = order.getId();
		this.customerId = order.getCustomer().getId();
		this.total = order.getTotal();
		this.orderNumber = order.getOrderNumber();
		this.products = order.getProducts();
		this.status = order.getOrderStatus();
		this.isPaid = order.getIsPaid();
		this.createdAt = order.getCreateAt();
		this.updatedAt = order.getUpdateAt();
	}
	
	public Order toOrder() {
		return new Order(id, new Customer(customerId), total, orderNumber, createdAt, updatedAt, status, products,null,  isPaid);
	}
	
	public OrderResponseDTO toOrderResponse() {
		return new OrderResponseDTO(id,orderNumber);
	}

}
