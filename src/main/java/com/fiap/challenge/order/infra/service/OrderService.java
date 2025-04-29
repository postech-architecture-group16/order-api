package com.fiap.challenge.order.infra.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;
import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.usecases.order.ConfirmPaymentUseCase;
import com.fiap.challenge.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.challenge.order.application.usecases.order.FindOrderUseCase;
import com.fiap.challenge.order.infra.database.entities.CustomerEntity;
import com.fiap.challenge.order.infra.database.entities.OrderEntity;
import com.fiap.challenge.order.infra.database.repositories.CustomersRepository;
import com.fiap.challenge.order.infra.database.repositories.OrderRepository;
import com.fiap.challenge.order.infra.database.repositories.ProductRepository;
import com.fiap.challenge.order.infra.mq.MqProducerProduction;

@Service
public class OrderService implements CreateOrderUseCase, FindOrderUseCase, ConfirmPaymentUseCase {

	private OrderRepository orderRepository;
	private CustomersRepository customersRepository;
	private ProductRepository productRepository;
	private MqProducerProduction mqProducerProduction;
	
    
	public OrderService(@Autowired OrderRepository orderRepository, 
			@Autowired CustomersRepository customersRepository,
			@Autowired ProductRepository productRepository,
			@Autowired MqProducerProduction mqProducerProduction) {
		this.orderRepository = orderRepository;
		this.customersRepository = customersRepository;
		this.productRepository = productRepository;
		this.mqProducerProduction = mqProducerProduction;
	}

	@Override
	public Order createOrder(Order order) {
		BigDecimal total = order.getProducts().stream().map(OrderProduct::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
		Long nextOrderNumber = getNextOrderNumber();

		order.setTotal(total);
		order.setCreateAt(LocalDateTime.now());
		order.setOrderNumber(Objects.equals(0L,nextOrderNumber) ? 1 : ++nextOrderNumber);
		order.setProducts(getOrderProducts(order));
		
		if (Objects.nonNull(order.getCustomer())) {
			Customer customer = this.getCustomerById(order.getCustomer().getId());
			order.setCustomer(customer);
		}
		
		return orderRepository.save(new OrderEntity(order)).toOrder();
	}
	

	@Override
	public Order findById(UUID id) {
		OrderEntity order = orderRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		CustomerEntity customerEntity = customersRepository.findById(order.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		Customer customer = customerEntity.toCustomer();
		return order
				.toOrder(customer);
	}
	
	private Customer getCustomerById(UUID customerId) {
		return customersRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found")).toCustomer();
	}
	
	private Long getNextOrderNumber() {
	  return Objects.isNull(orderRepository.findLastOrderNumber()) ? 0L : orderRepository.findLastOrderNumber();
    }
	
	private List<OrderProduct> getOrderProducts(Order order) {
		var productIdList = order.getProducts().stream().map(OrderProduct::getProductId).toList();

		return productIdList.stream().map(uuid -> {
			Product product = productRepository.findById(uuid).get().toProduct();
			return new OrderProduct(product.getId(), product.getPrice(), product.getName(), order.getCreateAt());
		}).toList();
	}

	@Override
	public void confirmPayment(UUID orderId, String paymentId, Boolean isPaid, Long orderNumber) {
		OrderEntity entity = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		entity.setPaymentId(paymentId);
		entity.setIsPaid(isPaid);
		
		orderRepository.save(entity);
		
		try {
			mqProducerProduction.send(orderRepository.save(entity).toOrder());
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error while converting order to JSON", e);
		} catch (AmqpException e) {
			throw new RuntimeException("Error while sending order to MQ", e);
		}
		
	}


}
