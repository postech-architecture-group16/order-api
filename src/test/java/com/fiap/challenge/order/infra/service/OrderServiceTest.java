package com.fiap.challenge.order.infra.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;
import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;
import com.fiap.challenge.order.infra.database.entities.CustomerEntity;
import com.fiap.challenge.order.infra.database.entities.OrderEntity;
import com.fiap.challenge.order.infra.database.entities.ProductEntity;
import com.fiap.challenge.order.infra.database.repositories.CustomersRepository;
import com.fiap.challenge.order.infra.database.repositories.OrderRepository;
import com.fiap.challenge.order.infra.database.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	
	@InjectMocks OrderService orderService;
	
	@Mock OrderRepository orderRepository;
	@Mock CustomersRepository customersRepository;
	@Mock ProductRepository productRepository;
	
	private OrderProduct orderProduct;
	
	@BeforeEach
	public void setUp() {
		orderProduct = new OrderProduct( UUID.randomUUID(), BigDecimal.valueOf(10.0), "mockProduct", LocalDateTime.now());
		orderService = new OrderService(orderRepository, customersRepository, productRepository);
	}
	
	 @Test
	    void createOrderShouldSaveOrderWithCalculatedTotalAndOrderNumber() {
		 
		 	UUID customerId = UUID.randomUUID();
		 	Customer customer = new Customer(customerId);
		 	customer.setDocumentId("12345678901");
	        Order order = new Order(UUID.randomUUID(),
	        		customer, 
	        		BigDecimal.valueOf(20.0), 
	        		2L,
	        		LocalDateTime.now(),
	        		List.of(orderProduct,orderProduct),
	        		false);
	        Product product = new Product(orderProduct.getProductId(), 
	        		orderProduct.getProductName(), 
	        		CategorieEnums.LANCHE, 
	        		BigDecimal.valueOf(10.0),
	        		"Description1");
	        
	        OrderEntity savedEntity = new OrderEntity(order);
	        CustomerEntity customerEntity = new CustomerEntity(customer);
	        ProductEntity productEntity = new ProductEntity(product); 
	        
	        when(orderRepository.findLastOrderNumber()).thenReturn(1L);
	        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedEntity);
	        when(customersRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
	        when(productRepository.findById(orderProduct.getProductId())).thenReturn(Optional.of(productEntity));
	        
	        Order result = orderService.createOrder(order);

	        Assertions.assertEquals(2L, result.getOrderNumber());
	        Assertions.assertNotNull(result.getCreateAt());
	    }
	 
	 @Test
	    void createOrderShouldSaveOrderWithCalculatedTotalOrderNumberNull() {
		 
		 	UUID customerId = UUID.randomUUID();
		 	Customer customer = new Customer(customerId);
		 	customer.setDocumentId("12345678901");
	        Order order = new Order(UUID.randomUUID(),
	        		customer, 
	        		BigDecimal.valueOf(20.0), 
	        		1L,
	        		LocalDateTime.now(),
	        		List.of(orderProduct,orderProduct),
	        		false);
	        Product product = new Product(orderProduct.getProductId(), orderProduct.getProductName(), CategorieEnums.LANCHE, BigDecimal.valueOf(10.0),"Description1");
	        OrderEntity savedEntity = new OrderEntity(order);
	        CustomerEntity customerEntity = new CustomerEntity(customer);
	        ProductEntity productEntity = new ProductEntity(product); 
	        
	        when(orderRepository.findLastOrderNumber()).thenReturn(null);
	        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedEntity);
	        when(customersRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
	        when(productRepository.findById(orderProduct.getProductId())).thenReturn(Optional.of(productEntity));

	        Order result = orderService.createOrder(order);

	        Assertions.assertEquals(1L, result.getOrderNumber());
	        Assertions.assertNotNull(result.getId());
	    }
	 
	 @Test
	    void createOrderShouldSaveOrderWithoutCustomer() {
		 Product product = new Product(orderProduct.getProductId(), 
				 orderProduct.getProductName(), 
				 CategorieEnums.LANCHE, 
				 BigDecimal.valueOf(10.0),
				 "Description1");
	        Order order = new Order(UUID.randomUUID(),
	        		null, 
	        		BigDecimal.valueOf(20.0), 
	        		2L,
	        		LocalDateTime.now(),
	        		List.of(orderProduct,orderProduct),
	        		false);
	        OrderEntity savedEntity = new OrderEntity(order);
	        ProductEntity productEntity = new ProductEntity(product); 

	        when(orderRepository.findLastOrderNumber()).thenReturn(1L);
	        when(productRepository.findById(orderProduct.getProductId())).thenReturn(Optional.of(productEntity));
	        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedEntity);

	        Order result = orderService.createOrder(order);

	        Assertions.assertNull(result.getCustomer());
	        Assertions.assertEquals(2L, result.getOrderNumber());
	        Assertions.assertNotNull(result.getCreateAt());
	    }

	    @Test
	    void createOrderShouldThrowExceptionWhenCustomerNotFound() {
	        Order order = new Order(new Customer(UUID.randomUUID()), List.of(
	        		orderProduct)
	        , false);

//	        when(customersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

	        Assertions.assertThrows(RuntimeException.class, () -> orderService.createOrder(order));
	    }
	    
	    @Test
	    void findByIdShouldReturnOrderWhenOrderExists() {
	        UUID orderId = UUID.randomUUID();
	        OrderEntity orderEntity = new OrderEntity(
	        		orderId,
	        		UUID.randomUUID(),
	        		BigDecimal.valueOf(100.0), 
	        		List.of(orderProduct), 
	        		"paymentId",
	        		false, 
	        		LocalDateTime.now());

	        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
	        when(customersRepository.findById(orderEntity.getCustomerId())).thenReturn(Optional.of(new CustomerEntity()));
	        Order result = orderService.findById(orderId);

	        Assertions.assertEquals(orderId, result.getId());
	    }
	    

	    @Test
	    void findByIdShouldThrowExceptionWhenOrderDoesNotExist() {
	        UUID orderId = UUID.randomUUID();

	        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

	        Assertions.assertThrows(RuntimeException.class, () -> orderService.findById(orderId));
	    }
	    

	    @Test
	    void confirmPaymentShouldUpdateOrderWhenOrderExists() {
	        // Arrange
	        UUID orderId = UUID.randomUUID();
	        String paymentId = "payment-123";
	        Boolean isPaid = true;
	        Long orderNumber = 456L;

	        OrderEntity existingOrder = new OrderEntity();
	        existingOrder.setId(orderId);

	        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

	        // Act
	        orderService.confirmPayment(orderId, paymentId, isPaid, orderNumber);

	        // Assert
	        verify(orderRepository, times(1)).findById(orderId);
	        verify(orderRepository, times(1)).save(existingOrder);

	        Assertions.assertEquals(paymentId, existingOrder.getPaymentId());
	        Assertions.assertEquals(isPaid, existingOrder.getIsPaid());
	    }
	
}
