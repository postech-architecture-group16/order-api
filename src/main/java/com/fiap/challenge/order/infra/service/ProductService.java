package com.fiap.challenge.order.infra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;
import com.fiap.challenge.order.application.usecases.product.CreateProductUseCase;
import com.fiap.challenge.order.application.usecases.product.FindByCategorieUseCase;
import com.fiap.challenge.order.infra.database.entities.ProductEntity;
import com.fiap.challenge.order.infra.database.repositories.ProductRepository;

@Service
public class ProductService implements CreateProductUseCase, FindByCategorieUseCase{

	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void create(Product product) {
		productRepository.save(new ProductEntity(product));
	}

	@Override
	public List<Product> findByCategorie(CategorieEnums categorie) {
		return productRepository.findByCategory(categorie).stream()
				.map(ProductEntity::toProduct)
				.toList();
	}

}
