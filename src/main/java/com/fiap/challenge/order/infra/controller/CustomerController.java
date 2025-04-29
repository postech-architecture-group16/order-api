package com.fiap.challenge.order.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.infra.models.dto.request.CustomerRequestDTO;
import com.fiap.challenge.order.infra.service.CustomerService;

@RestController
@RequestMapping("/customer/api")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(@Autowired CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
		Customer customerResponse = customerService.createCustomer(customerRequestDTO.toCustomer(customerRequestDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
				
	}
	
	@GetMapping("/{documentId}")
	public ResponseEntity<Customer> findByDocumentId(@PathVariable("documentId") String documentId){
		Customer customerResponse = customerService.findCustomerByDocumentId(documentId);
		return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
	}
}
