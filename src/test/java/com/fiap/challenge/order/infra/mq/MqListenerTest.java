package com.fiap.challenge.order.infra.mq;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.challenge.order.infra.models.dto.response.PaymentResponseDTO;
import com.fiap.challenge.order.infra.service.OrderService;


@ExtendWith(MockitoExtension.class)
class MqListenerTest {

	@Mock
    private OrderService orderService;

    @InjectMocks
    private MqListener mqListener;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mqListener = new MqListener(orderService);
    }

    @Test
    void receiveShouldCallOrderServiceWithCorrectArguments() throws JsonProcessingException {
        // Arrange
    	
    	UUID orderId = UUID.randomUUID();
    	UUID paymentId = UUID.randomUUID();
        PaymentResponseDTO paymentResponse = new PaymentResponseDTO(
        		orderId,
        		paymentId,
            Boolean.TRUE,
            789L
        );
        String message = objectMapper.writeValueAsString(paymentResponse);

        // Act
        mqListener.receive(message);

        // Assert
        verify(orderService, times(1)).confirmPayment(
            orderId,
            String.valueOf(paymentId),
            true,
            789L
        );
    }
}
