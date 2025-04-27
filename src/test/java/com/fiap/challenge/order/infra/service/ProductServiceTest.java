package com.fiap.challenge.order.infra.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;
import com.fiap.challenge.order.infra.database.entities.OrderEntity;
import com.fiap.challenge.order.infra.database.entities.ProductEntity;
import com.fiap.challenge.order.infra.database.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createShouldSaveProductEntity() {
        Product product = new Product(
        		UUID.randomUUID(), 
        		"Product1", 
        		CategorieEnums.ACOMPANHAMENTO, 
        		BigDecimal.valueOf(10.0), 
        		"Description");
        ProductEntity productEntity = new ProductEntity(product);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        
        Assertions.assertDoesNotThrow(() -> productService.create(product));
    }

    @Test
    void findByCategorieShouldReturnMappedProductsWhenCategoryExists() {
        CategorieEnums category = CategorieEnums.LANCHE;
        List<ProductEntity> productEntities = List.of(
            new ProductEntity(new Product(
            		UUID.randomUUID(), 
            		"Product1", 
            		CategorieEnums.ACOMPANHAMENTO, 
            		BigDecimal.valueOf(10.0), 
            		"Description")),
            new ProductEntity(new Product(
            		UUID.randomUUID(), 
            		"Product2", 
            		CategorieEnums.LANCHE, 
            		BigDecimal.valueOf(25.0), 
            		"Description2"))
        );
        when(productRepository.findByCategory(category)).thenReturn(productEntities);

        List<Product> result = productService.findByCategorie(category);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Product1", result.get(0).getName());
        Assertions.assertEquals("Product2", result.get(1).getName());
        verify(productRepository).findByCategory(category);
    }

    @Test
    void findByCategorieShouldReturnEmptyListWhenNoProductsExist() {
        CategorieEnums category = CategorieEnums.BEBIDA;
        when(productRepository.findByCategory(category)).thenReturn(List.of());

        List<Product> result = productService.findByCategorie(category);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        verify(productRepository).findByCategory(category);
    }
}