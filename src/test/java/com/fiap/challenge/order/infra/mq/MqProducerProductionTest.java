package com.fiap.challenge.order.infra.mq;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;

@ExtendWith(MockitoExtension.class)
class MqProducerProductionTest {

	@Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private Queue productionQueue;

    @InjectMocks
    private MqProducerProduction mqProducerProduction;

    private Order order;
    
    @BeforeEach
    void setUp() {
        order = new Order(
            UUID.randomUUID(),
            null,
            BigDecimal.valueOf(100.00),
            1L,
            LocalDateTime.now(),
            List.of(new OrderProduct(UUID.randomUUID(),
            		BigDecimal.valueOf(50.00),
            		"product1",
            		LocalDateTime.now())),
            "paymentId",
            Boolean.TRUE
        );

        when(productionQueue.getName()).thenReturn("test-queue");
    }

    @Test
    void sendShouldCallRabbitTemplateWithCorrectArguments() throws JsonProcessingException {
        mqProducerProduction.send(order);

        verify(rabbitTemplate, times(1)).convertAndSend(eq("test-queue"), anyString());
    }

    @Test
    void sendShouldThrowAmqpExceptionWhenRabbitTemplateFails() {
        doThrow(new AmqpException("AMQP error")).when(rabbitTemplate).convertAndSend(anyString(), anyString());

        Assertions.assertThrows(AmqpException.class, () -> mqProducerProduction.send(order));
    }
}
