package com.fiap.challenge.order.infra.database.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.challenge.order.application.domain.models.Customer;
import com.fiap.challenge.order.infra.database.entities.CustomerEntity;

public interface CustomersRepository extends JpaRepository<CustomerEntity, UUID>{
	
	CustomerEntity findByDocumentId(String documentId);

}
