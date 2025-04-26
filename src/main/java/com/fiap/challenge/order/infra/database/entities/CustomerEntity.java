package com.fiap.challenge.order.infra.database.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fiap.challenge.order.application.domain.models.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	private String name;

	private String documentId;

	private String email;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public CustomerEntity() {
	}

	public CustomerEntity(Customer customer) {
		this.id = customer.getId();
		this.name = customer.getName();
		this.documentId = customer.getDocumentId().replace(".", "").replace("-", "");
		this.email = customer.getEmail();
	}

	public Customer toCustomer() {
		return new Customer(id, name, email, documentId);
	}

}