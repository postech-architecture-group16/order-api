package com.fiap.challenge.order.infra.mq;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.challenge.order.application.domain.models.Order;
import com.fiap.challenge.order.application.domain.models.OrderProduct;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@ExtendWith(MockitoExtension.class)
class MqProducerProductionTest {

    @Mock
    private SqsTemplate sqsTemplate;

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

        // Mock the queue name
        when(productionQueue.getName()).thenReturn("order-production");
    }

    @Test
    void sendShouldCallSqsTemplateWithCorrectArguments() throws JsonProcessingException {
    	ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		
        mqProducerProduction.send(order);

        verify(sqsTemplate).send("order-production", writer.writeValueAsString(order));
    }
}