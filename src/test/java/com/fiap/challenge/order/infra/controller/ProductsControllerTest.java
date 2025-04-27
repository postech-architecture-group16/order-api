package com.fiap.challenge.order.infra.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;
import com.fiap.challenge.order.infra.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {

	@Mock
    private ProductService productService;

    @InjectMocks
    private ProductsController productsController;

    @Test
    void findProductsByCategoryShouldReturnProductsWhenCategoryExists() {
        CategorieEnums category = CategorieEnums.LANCHE;
        List<Product> products = List.of(
        		new Product(UUID.randomUUID(),"Product1", category, BigDecimal.valueOf(20L), "Description1"),
        		new Product(UUID.randomUUID(),"Product2", category, BigDecimal.valueOf(10L), "Description2"));
        when(productService.findByCategorie(category)).thenReturn(products);

        ResponseEntity<List<Product>> response = productsController.findProductsByCategory(category);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(products, response.getBody());
        verify(productService).findByCategorie(category);
    }

    @Test
    void findProductsByCategoryShouldReturnEmptyListWhenNoProductsExist() {
        CategorieEnums category = CategorieEnums.LANCHE;
        when(productService.findByCategorie(category)).thenReturn(List.of());

        ResponseEntity<List<Product>> response = productsController.findProductsByCategory(category);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().isEmpty());
        verify(productService).findByCategorie(category);
    }

    @Test
    void createProductShouldReturnCreatedStatus() {
        Product product = new Product(UUID.randomUUID(),"Product1", CategorieEnums.LANCHE, BigDecimal.valueOf(20L), "Description1");

        ResponseEntity<Void> response = productsController.createProduct(product);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(productService).create(product);
    }
}
