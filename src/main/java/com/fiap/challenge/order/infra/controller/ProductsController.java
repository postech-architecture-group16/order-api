package com.fiap.challenge.order.infra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;
import com.fiap.challenge.order.infra.service.ProductService;

@RestController
@RequestMapping("/products/api")
public class ProductsController {

    private  ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findProductsByCategory(@RequestParam CategorieEnums category){
        List<Product> products = productService.findByCategorie(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product){
		productService.create(product);
		return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
