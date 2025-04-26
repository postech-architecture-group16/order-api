package com.fiap.challenge.order.infra.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;
import com.fiap.challenge.order.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.challenge.order.application.usecases.order.FindOrderUseCase;
import com.fiap.challenge.order.infra.database.entities.OrderEntity;
import com.fiap.challenge.order.infra.database.repositories.CustomersRepository;
import com.fiap.challenge.order.infra.database.repositories.OrderRepository;

@Service
public class OrderService implements CreateOrderUseCase, FindOrderUseCase {

	private OrderRepository orderRepository;
	private CustomersRepository customersRepository;
	
    
	public OrderService(@Autowired OrderRepository orderRepository, @Autowired CustomersRepository customersRepository) {
		this.orderRepository = orderRepository;
		this.customersRepository = customersRepository;
	}

	@Override
	public Order createOrder(Order order) {
		BigDecimal total = order.getProducts().stream().map(OrderProduct::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		Long nextOrderNumber = getNextOrderNumber();

		order.setTotal(total);
		order.setCreateAt(LocalDateTime.now());
		order.setOrderStatus(OrderStatusEnum.EM_PREPARACAO);
		order.setOrderNumber(Objects.isNull(nextOrderNumber) ? 1 : nextOrderNumber+1);
		
		if (Objects.nonNull(order.getCustomer().getId())) {
			Customer customer = this.getCustomerById(order.getCustomer().getId());
			order.setCustomer(customer);
		}
		
		return orderRepository.save(new OrderEntity(order)).toOrder();
	}
	

	@Override
	public Order findById(UUID id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Order not found"))
				.toOrder();
	}
	
	private Customer getCustomerById(UUID customerId) {
		return customersRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found")).toCustomer();
	}
	
	private Long getNextOrderNumber() {
	  return orderRepository.findLastOrderNumber();
    }


}
