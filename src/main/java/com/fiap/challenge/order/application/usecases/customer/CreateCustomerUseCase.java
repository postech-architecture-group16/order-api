package com.fiap.challenge.order.application.usecases.customer;

import com.fiap.challenge.order.application.domain.models.Customer;

public interface CreateCustomerUseCase {

	Customer createCustomer(Customer customer);
	
}
