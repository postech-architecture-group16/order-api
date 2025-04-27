package com.fiap.challenge.order.infra.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.infra.database.entities.CustomerEntity;
import com.fiap.challenge.order.infra.database.repositories.CustomersRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomersRepository customersRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomerShouldSaveAndReturnCustomer() {
        Customer customer = new Customer("Name Lastname","email@email.com","123456789");
        CustomerEntity customerEntity = new CustomerEntity(customer);
        when(customersRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        Customer result = customerService.createCustomer(customer);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Name Lastname", result.getName());
        Assertions.assertEquals("123456789", result.getDocumentId());
    }

    @Test
    void findCustomerByDocumentIdShouldReturnCustomerWhenExists() {
        String documentId = "123456789";
        CustomerEntity customerEntity = new CustomerEntity(new Customer("Name Lastname","email@email.com", documentId));
        when(customersRepository.findByDocumentId(documentId)).thenReturn(customerEntity);

        Customer result = customerService.findCustomerByDocumentId(documentId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Name Lastname", result.getName());
        Assertions.assertEquals(documentId, result.getDocumentId());
        verify(customersRepository).findByDocumentId(documentId);
    }

    @Test
    void findCustomerByDocumentIdShouldThrowExceptionWhenNotFound() {
        String documentId = "123456789";
        when(customersRepository.findByDocumentId(documentId)).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> customerService.findCustomerByDocumentId(documentId));
        verify(customersRepository).findByDocumentId(documentId);
    }
}