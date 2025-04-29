package com.fiap.challenge.order.infra.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqSenderConfig {

	@Value("${queue.name.producer.payment}")
	private String message;
	
    @Value("${queue.name.producer.production}")
    private String productionQueueName;

	@Bean
	public Queue queue() {
		return new Queue(message, true);
	}
	
    @Bean
    public Queue productionQueue() {
        return new Queue(productionQueueName, true);
    }
}
