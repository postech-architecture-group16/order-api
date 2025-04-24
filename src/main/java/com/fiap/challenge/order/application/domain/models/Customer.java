package com.fiap.challenge.order.application.domain.models;

import java.util.UUID;

public class Customer {

	private UUID id;
	
	private String name;
	
	private String email;
	
	private String documentId;

	public Customer(UUID id, String name, String email, String documentId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.documentId = documentId;
	}

	public Customer() {
	}
	
	public Customer(String name, String email, String documentId) {
		super();
		this.name = name;
		this.email = email;
		this.documentId = documentId;
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
}
