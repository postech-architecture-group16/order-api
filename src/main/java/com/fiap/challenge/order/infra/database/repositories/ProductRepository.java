package com.fiap.challenge.order.infra.database.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;
import com.fiap.challenge.order.infra.database.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
	List<ProductEntity> findByCategory(CategorieEnums category);
}
