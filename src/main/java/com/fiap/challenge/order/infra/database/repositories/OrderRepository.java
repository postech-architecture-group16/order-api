package com.fiap.challenge.order.infra.database.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fiap.challenge.order.infra.database.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID>{
	@Query("SELECT p.orderNumber FROM OrderEntity p ORDER BY id DESC LIMIT 1")
	Long findLastOrderNumber();
}
