package com.fiap.challenge.order.infra.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.infra.models.dto.request.CustomerRequestDTO;
import com.fiap.challenge.order.infra.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    public void createCustomerShouldReturnCreatedResponseWhenValidRequest() {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO("name", "email", "documentId");
        Customer expectedCustomer = new Customer("name", "email", "documentId");

        when(customerService.createCustomer(any(Customer.class))).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.createCustomer(requestDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedCustomer, response.getBody());
    }

    @Test
    void findByDocumentId_ReturnsOkStatusAndCustomerResponse_WhenCustomerExists() {
        String documentId = "123456789";
        Customer expectedCustomer = new Customer("name", "email", "documentId");

        when(customerService.findCustomerByDocumentId(eq(documentId))).thenReturn(expectedCustomer);

        ResponseEntity<Customer> response = customerController.findByDocumentId(documentId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedCustomer, response.getBody());
    }

//    @Test
//    void createCustomerShouldReturnBadRequestWhenRequestIsInvalid() {
//        CustomerRequestDTO invalidRequestDTO = new CustomerRequestDTO(null, "email", "documentId");
//
//        ResponseEntity<Customer> response = customerController.createCustomer(invalidRequestDTO);
//
//        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }

//    @Test
//    void findByDocumentId_ReturnsNotFoundStatus_WhenCustomerDoesNotExist() {
//        String documentId = "nonexistent";
//        
//        when(customerService.findCustomerByDocumentId(eq(documentId))).thenReturn(null);
//
//        ResponseEntity<Customer> response = customerController.findByDocumentId(documentId);
//
//        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}
