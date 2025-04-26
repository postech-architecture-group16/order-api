package com.fiap.challenge.order.infra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;
import com.fiap.challenge.order.application.domain.models.enums.OrderStatusEnum;
import com.fiap.challenge.order.infra.database.entities.CustomerEntity;
import com.fiap.challenge.order.infra.database.entities.OrderEntity;
import com.fiap.challenge.order.infra.database.repositories.CustomersRepository;
import com.fiap.challenge.order.infra.database.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	
	@InjectMocks OrderService orderService;
	
	@Mock OrderRepository orderRepository;
	@Mock CustomersRepository customersRepository;
	private OrderProduct orderProduct;
	
	@BeforeEach
	public void setUp() {
		orderProduct = new OrderProduct();
		orderProduct.setId(UUID.randomUUID());
		orderProduct.setOrderId(UUID.randomUUID());
		orderProduct.setProductId(UUID.randomUUID());
		orderProduct.setPrice(BigDecimal.valueOf(10.0));
		orderProduct.setProductName("mockProduct");
		orderProduct.setCreatedAt(LocalDateTime.now());
		
		orderService = new OrderService(orderRepository, customersRepository);
	}
	
	 @Test
	    void createOrderShouldSaveOrderWithCalculatedTotalAndOrderNumber() {
		 
	        Order order = new Order(UUID.randomUUID(),
	        		new Customer(), 
	        		BigDecimal.valueOf(20.0), 
	        		2L,
	        		LocalDateTime.now(),
	        		LocalDateTime.now(),
	        		OrderStatusEnum.EM_PREPARACAO,
	        		List.of(orderProduct,orderProduct),
	        		null,
	        		false);
	        OrderEntity savedEntity = new OrderEntity(order);

	        when(orderRepository.findLastOrderNumber()).thenReturn(1L);
	        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedEntity);
	        

	        Order result = orderService.createOrder(order);

	        assertEquals(2L, result.getOrderNumber());
	        assertNotNull(result.getCreateAt());
	    }

	    @Test
	    void createOrderShouldThrowExceptionWhenCustomerNotFound() {
	        Order order = new Order(new Customer(UUID.randomUUID()), List.of(
	        		orderProduct)
	        , false);

	        when(customersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

	        assertThrows(RuntimeException.class, () -> orderService.createOrder(order));
	    }

	    @Test
	    void findByIdShouldReturnOrderWhenOrderExists() {
	        UUID orderId = UUID.randomUUID();
	        OrderEntity orderEntity = new OrderEntity(
	        		orderId, 
	        		UUID.randomUUID(), 
	        		BigDecimal.valueOf(100.0), 
	        		1L,
	        		List.of(orderProduct), 
	        		OrderStatusEnum.EM_PREPARACAO, 
	        		false, 
	        		LocalDateTime.now(), 
	        		LocalDateTime.now());

	        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));

	        Order result = orderService.findById(orderId);

	        assertEquals(orderId, result.getId());
	        assertEquals(1L, result.getOrderNumber());
	    }

	    @Test
	    void findByIdShouldThrowExceptionWhenOrderDoesNotExist() {
	        UUID orderId = UUID.randomUUID();

	        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

	        assertThrows(RuntimeException.class, () -> orderService.findById(orderId));
	    }
	
}
