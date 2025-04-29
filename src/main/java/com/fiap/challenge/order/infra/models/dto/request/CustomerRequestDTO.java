package com.fiap.challenge.order.infra.models.dto.request;

import com.fiap.challenge.order.application.domain.models.Customer;

public record CustomerRequestDTO( String name, String email, String documentId){

	public Customer toCustomer(CustomerRequestDTO customerRequestDTO) {
		return new Customer(customerRequestDTO.name(), customerRequestDTO.email(), customerRequestDTO.documentId());
	}
}
