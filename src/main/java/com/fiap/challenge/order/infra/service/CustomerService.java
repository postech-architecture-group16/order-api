package com.fiap.challenge.order.infra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.application.usecases.customer.CreateCustomerUseCase;
import com.fiap.challenge.order.application.usecases.customer.FindCustomerByDocumentIdUseCase;
import com.fiap.challenge.order.infra.database.entities.CustomerEntity;
import com.fiap.challenge.order.infra.database.repositories.CustomersRepository;

@Service
public class CustomerService implements CreateCustomerUseCase, FindCustomerByDocumentIdUseCase{

	private CustomersRepository customersRepository;
	
	public CustomerService(@Autowired CustomersRepository customersRepository) {
		this.customersRepository = customersRepository;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customersRepository.save(new CustomerEntity(customer)).toCustomer();
	}

	@Override
	public Customer findCustomerByDocumentId(String documentId) {
		return customersRepository.findByDocumentId(documentId).toCustomer();
	}

}
