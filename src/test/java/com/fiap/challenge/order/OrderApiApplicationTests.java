package com.fiap.challenge.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@SpringBootTest(properties = "spring.profiles.active=test")
class OrderApiApplicationTests {

    @MockBean
    private SqsTemplate sqsTemplate;
	
	@Test
	void contextLoads() {
	}

}
