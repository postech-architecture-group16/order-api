package com.fiap.challenge.order.infra.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;
import com.fiap.challenge.order.infra.models.dto.request.OrderRequestDTO;
import com.fiap.challenge.order.infra.models.dto.response.OrderResponseDTO;
import com.fiap.challenge.order.infra.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;
    
    private OrderRequestDTO orderRequestDTO;
    private OrderProduct product;
    
    @BeforeEach
    public void setUp() {
    	product = new OrderProduct(UUID.randomUUID(), UUID.randomUUID(),UUID.randomUUID(), null,  "mockProduct",LocalDateTime.now());
		orderRequestDTO = new OrderRequestDTO("123e4567-e89b-12d3-a456-426614174000", List.of(product));
		
	}

    @Test
    void createOrderShouldReturnCreatedResponseWhenValidRequest() {
        Order order = new Order(new Customer(UUID.fromString(orderRequestDTO.customerId())), orderRequestDTO.products(), Boolean.FALSE);
        OrderResponseDTO expectedResponse = new OrderResponseDTO(order);

        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(orderRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void createOrderShouldThrowExceptionWhenInvalidCustomerId() {
        assertThrows(NullPointerException.class, () -> orderController.createOrder(orderRequestDTO));
    }

    @Test
    void findOrderShouldReturnOrderWhenIdExists() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(new Customer(orderId), List.of(product), Boolean.FALSE);

        when(orderService.findById(orderId)).thenReturn(order);

        ResponseEntity<Order> response = orderController.findOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    void findOrderShouldReturnNotFoundWhenIdDoesNotExist() {
        UUID orderId = UUID.randomUUID();

        when(orderService.findById(orderId)).thenThrow(new EntityNotFoundException("Order not found"));

        assertThrows(EntityNotFoundException.class, () -> orderController.findOrder(orderId));
    }
}
